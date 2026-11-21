package pl.piotrtrybala.database.executor.async;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.engine.Context;

public class Payload {

    private final Context context;
    private final Command command;

    public Payload(Context context, Command command) {
        this.context = context;
        this.command = command;
    }

    public Context getContext() {
        return context;
    }

    public Command getCommand() {
        return command;
    }
}
