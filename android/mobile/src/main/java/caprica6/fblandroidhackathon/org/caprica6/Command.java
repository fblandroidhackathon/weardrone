package caprica6.fblandroidhackathon.org.caprica6;

public enum Command {

    TAKEOFF("take off"),
    LAND("land"),
    FORWARD("forward"),
    BACK("back"),
    MOVE_LEFT("move left"),
    MOVE_RIGHT("move right"),
    TURN_LEFT("turn left"),
    TURN_RIGHT("turn right"),
    UP("up"),
    DOWN("down"),
    VOICE("voice"),
    FLIP("flip"),
    INVALID("invalid");


    public final static String commandParseObject = "Commands";
    public final String command;

    private Command(String command) {
        this.command = command;
    }

}
