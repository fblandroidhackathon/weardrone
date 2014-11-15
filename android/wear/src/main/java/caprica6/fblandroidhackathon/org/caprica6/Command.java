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

    public static Command getByName(String name) {
        for(Command command : Command.values()){
            if(command.command.equalsIgnoreCase(name)){
                return command;
            }
        }
        return INVALID;
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
