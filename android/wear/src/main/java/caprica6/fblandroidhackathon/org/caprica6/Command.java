package caprica6.fblandroidhackathon.org.caprica6;

public enum Command {

    TAKEOFF("takeoff"),
    LAND("land"),
    FORWARD("forward"),
    BACK("back"),
    MOVE_LEFT("move_left"),
    MOVE_RIGHT("move_right"),
    TURN_LEFT("turn_left"),
    TURN_RIGHT("turn_right"),
    UP("up"),
    DOWN("down"),
    VOICE("voice"),
    INVALID("invalid");


    public final static String commandParseObject = "Commands";
    public final String command;

    private Command(String command) {
        this.command = command;
    }

    public static Command getCommandFromPosition(int pos) {
        switch (pos) {
            case 1:
                return UP;
            case 3:
                return TURN_LEFT;
            case 4:
                return VOICE;
            case 5:
                return TURN_RIGHT;
            case 7:
                return DOWN;
            default:
                return INVALID;
        }
    }
}
