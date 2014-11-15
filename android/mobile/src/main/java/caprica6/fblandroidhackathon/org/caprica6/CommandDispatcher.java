package caprica6.fblandroidhackathon.org.caprica6;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class CommandDispatcher {

    public static void dispatch(Command command) {
        ParseObject po = new ParseObject(Command.commandParseObject);
        po.put("cmd", command.command);
        po.saveInBackground(new LoggingSaveCallback(command));
    }

    public static class LoggingSaveCallback extends SaveCallback {

        private Command cmd;

        LoggingSaveCallback(Command command) {
            cmd = command;
        }

        @Override
        public void done(ParseException e) {
            if (e == null) {
                Log.i("ParseWearDrone", String.format("Command %s sent", cmd.command));
            } else {
                Log.i("ParseWearDrone", String.format("Command not sent due to error", e.getMessage()));
            }
        }
    }

}
