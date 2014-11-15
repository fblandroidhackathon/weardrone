package caprica6.fblandroidhackathon.org.caprica6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    private static final List<String> COMMAND_WORDS = new ArrayList<String>();

    static {
        COMMAND_WORDS.add("noop");
        COMMAND_WORDS.add("land");
        COMMAND_WORDS.add("panic"); //land
        COMMAND_WORDS.add("take"); //take off
/*
        COMMAND_WORDS.add("left");
        COMMAND_WORDS.add("right");
        COMMAND_WORDS.add("up");
        COMMAND_WORDS.add("down");
        COMMAND_WORDS.add("degrees");
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
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mDebugTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
        stub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer();
            }
        });
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
            StringBuilder sb = new StringBuilder(mDebugTextView.getText());
            sb.append(spokenText);
            sb.append("->");
            sb.append(foundCommand);
            mDebugTextView.setText(sb.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String mapCommands(List<String> results) {
        for(String candidate : results) {
            for(String command : COMMAND_WORDS) {
                if(candidate.contains(command)){
                    sendCommand(command);
                    return command;
                }
            }
        }
        return "noop";
    }

    private void sendCommand(String command) {
        //TODO dispatch message to parse from here
    }
}
