package caprica6.fblandroidhackathon.org.caprica6;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommandListAdapter extends BaseAdapter {
    private Context mContext;
    private WindowManager mWindowManager;

    public CommandListAdapter(Context context) {
        super();
        mContext = context;
        mWindowManager =  (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int screenHeight = mWindowManager.getDefaultDisplay().getHeight();
        ViewGroup commandView = (ViewGroup) convertView;
        if (commandView == null) {
            commandView = (ViewGroup) View.inflate(mContext, R.layout.action_button, null);
        }
        commandView.setMinimumHeight(screenHeight / 3);
        TextView commandLabel = (TextView) commandView.findViewById(R.id.actionTextView);
        commandLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        switch (position) {
            case 1:
                commandLabel.setText("Up");
                commandLabel.setTag(Command.UP);
                break;
            case 3:
                commandLabel.setText("Left");
                commandLabel.setTag(Command.TURN_LEFT);
                break;
            case 4:
                commandLabel.setText("Voice");
                break;
            case 5:
                commandLabel.setText("Right");
                commandLabel.setTag(Command.TURN_RIGHT);
                break;
            case 7:
                commandLabel.setText("Down");
                commandLabel.setTag(Command.DOWN);
                break;
            default:
                break;
        }
        return commandView;
    }
}
