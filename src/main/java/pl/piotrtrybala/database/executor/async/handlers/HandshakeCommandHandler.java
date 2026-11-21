package pl.piotrtrybala.database.executor.async.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.HandshakeResult;

public class HandshakeCommandHandler implements CommandHandler{

    @Override
    public AsyncResult handle(Context context, Command command) {
        return new HandshakeResult();
    }
}
