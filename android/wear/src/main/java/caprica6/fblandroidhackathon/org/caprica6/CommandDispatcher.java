package caprica6.fblandroidhackathon.org.caprica6;

import com.parse.ParseObject;

public class CommandDispatcher {

    public static void dispatch(Command command) {
        ParseObject po = new ParseObject(Command.commandParseObject);
        po.add("cmd", command.command);
        po.saveInBackground();
    }

}
