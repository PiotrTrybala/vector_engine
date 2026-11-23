package pl.piotrtrybala.database.executor.async;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.enums.CommandType;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.async.handlers.AuthCommandHandler;
import pl.piotrtrybala.database.executor.async.handlers.CommandHandler;
import pl.piotrtrybala.database.executor.async.handlers.HandshakeCommandHandler;
import pl.piotrtrybala.database.executor.async.handlers.ShowCommandHandler;
import pl.piotrtrybala.database.executor.enums.ExecutorError;
import java.util.HashMap;
import java.util.Map;

public class AsyncExecutor implements Executor<AsyncResult, Payload> {

    private static final Logger LOGGER = LogManager.getLogger(AsyncExecutor.class.getName());

    private final Map<CommandType, CommandHandler> handlers;

    public AsyncExecutor() {
        this.handlers = new HashMap<>();

        this.handlers.put(CommandType.HANDSHAKE, new HandshakeCommandHandler());
        this.handlers.put(CommandType.AUTH, new AuthCommandHandler());
        this.handlers.put(CommandType.SHOW, new ShowCommandHandler());
    }

    @Override
    public AsyncResult execute(Payload payload) {
        LOGGER.info("processing query...");

        LOGGER.info("dispatching {}", payload.getCommand().getQueryType().toString());

        CommandHandler handler = this.handlers.get(payload.getCommand().getQueryType());
        if (handler == null) {
            return new Error(ExecutorError.UNSUPPORTED_COMMAND_TYPE);
        }

        return handler.handle(payload.getContext(), payload.getCommand());
    }
}
