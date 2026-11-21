package pl.piotrtrybala.database.executor.sync;

import pl.piotrtrybala.database.command.Command;

public class Payload {

    private final Command command;

    public Payload(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return "ExecutorPayload{" +
                "command=" + command +
                '}';
    }
}
