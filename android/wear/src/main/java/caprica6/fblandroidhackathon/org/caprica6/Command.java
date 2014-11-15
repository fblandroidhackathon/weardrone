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
    DOWN("down");

    public final String command;

    private Command(String command) {
        this.command = command;
    }

}
