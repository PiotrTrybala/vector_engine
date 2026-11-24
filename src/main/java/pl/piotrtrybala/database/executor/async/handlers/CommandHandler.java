package pl.piotrtrybala.database.executor.async.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.security.middleware.Middleware;

public interface CommandHandler {
    AsyncResult handle(Context context, Command command);
    Middleware<Context> getMiddleware();
}
