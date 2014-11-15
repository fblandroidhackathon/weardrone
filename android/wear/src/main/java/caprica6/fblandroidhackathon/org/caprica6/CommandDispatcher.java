package caprica6.fblandroidhackathon.org.caprica6;

import com.parse.ParseObject;

public class CommandDispatcher {

    public static void dispatch(Command command) {
        ParseObject po = new ParseObject(command.command);
        po.saveInBackground();
    }

}
