package caprica6.fblandroidhackathon.org.caprica6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";

    //private static final List<String> COMMAND_WORDS = new ArrayList<String>();
    private static final Map<String,Command> COMMAND_WORDS = new HashMap<String, Command>();

    private GoogleApiClient mGoogleApiClient;

    static {
        //COMMAND_WORDS.put("noop");
        COMMAND_WORDS.put("land",Command.LAND);
        COMMAND_WORDS.put("panic",Command.LAND); //land
        COMMAND_WORDS.put("take off",Command.TAKEOFF); //take off
        COMMAND_WORDS.put("left",Command.TURN_LEFT);
        COMMAND_WORDS.put("right",Command.TURN_RIGHT);
        COMMAND_WORDS.put("up",Command.UP);
        COMMAND_WORDS.put("down",Command.DOWN);
        COMMAND_WORDS.put("forward",Command.FORWARD);
        COMMAND_WORDS.put("back",Command.BACK);
/*        COMMAND_WORDS.add("degrees");
        COMMAND_WORDS.add("units");
        COMMAND_WORDS.add("execute");
        COMMAND_WORDS.add("clear"); //all commands
        COMMAND_WORDS.add("remove"); //last command
        COMMAND_WORDS.add("flip");
        COMMAND_WORDS.add("10");
        COMMAND_WORDS.add("15");
        COMMAND_WORDS.add("20");
        COMMAND_WORDS.add("25");
        COMMAND_WORDS.add("30");
        COMMAND_WORDS.add("35");
        COMMAND_WORDS.add("40");
        COMMAND_WORDS.add("45");
        COMMAND_WORDS.add("60");
        COMMAND_WORDS.add("90");
        COMMAND_WORDS.add("135");
        COMMAND_WORDS.add("180");
        COMMAND_WORDS.add("270");
        COMMAND_WORDS.add("360");
        COMMAND_WORDS.add("position");
*/
    }

    private static final int SPEECH_REQUEST_CODE = 1;

    private TextView mDebugTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = createGoogleWearableAPIClient();

        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mDebugTextView = (TextView) stub.findViewById(R.id.text);
                final GridView view = (GridView) findViewById(R.id.gridView);
                view.setAdapter(new CommandListAdapter(MainActivity.this));
                view.setOnItemClickListener(MainActivity.this);
            }
        });
        stub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer();
            }
        });
    }

    private GoogleApiClient createGoogleWearableAPIClient() {
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                        // Now you can use the data layer API
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onDisconnected "+ cause);
                    }

                })
                .addOnConnectionFailedListener(new OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                        // Request access only to the Wearable API
                .addApi(Wearable.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            String foundCommand = mapCommands(results);
            String spokenText = results.get(0);
//            StringBuilder sb = new StringBuilder(mDebugTextView.getText());
//            sb.append(spokenText);
//            sb.append("->");
//            sb.append(foundCommand);
//            mDebugTextView.setText(sb.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String mapCommands(List<String> results) {
        for(String candidate : results) {
            for(String command : COMMAND_WORDS.keySet()) {
                if(candidate.equalsIgnoreCase(command)){
                    sendCommand(Command.valueOf(command.toUpperCase()));
                    return command;
                }
            }
        }
        return "noop";
    }

    private void sendCommand(Command command) {
        PutDataMapRequest dataMap = PutDataMapRequest.create("/drone_command");
        dataMap.getDataMap().putInt("COMMAND", command.ordinal());
        PutDataRequest request = dataMap.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
                .putDataItem(mGoogleApiClient, request);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Command uiCommand = Command.getCommandFromPosition(position);
        if(uiCommand.equals(Command.INVALID))
            return;
        if(uiCommand.equals(Command.VOICE)) {
            displaySpeechRecognizer();
        } else {
            sendCommand(uiCommand);
        }
    }
}
