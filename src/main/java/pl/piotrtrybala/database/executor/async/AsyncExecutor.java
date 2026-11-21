package pl.piotrtrybala.database.executor.async;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.enums.CommandType;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.async.handlers.CommandHandler;
import pl.piotrtrybala.database.executor.async.handlers.HandshakeCommandHandler;
import pl.piotrtrybala.database.executor.async.results.AuthResult;
import pl.piotrtrybala.database.executor.async.results.HandshakeResult;
import pl.piotrtrybala.database.executor.enums.ExecutorError;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AsyncExecutor implements Executor<AsyncResult, Payload> {

    private static final Logger LOGGER = LogManager.getLogger(AsyncExecutor.class.getName());

//    private static class Handler {
//        private final CommandHandler result;
//
//        public Handler(Class<? extends CommandHandler> result) { this.result = result; }
//
//        public AsyncResult dispatch(Command command) {
//            try {
//                CommandHandler handler = result.getConstructor(command.getClass()).newInstance();
//                return handler.handle(command);
//            } catch(InvocationTargetException e) {
//                Throwable cause = e.getCause();
//                if (cause instanceof RuntimeException) {
//                    throw (RuntimeException) cause;
//                }
//                return new Error(ExecutorError.INVALID_RESULT_TYPE);
//            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
//                LOGGER.error("error with dispatching {}: {}", command.getQueryType().toString(), e.getMessage());
//                return new Error(ExecutorError.INVALID_RESULT_TYPE);
//            }
//        }
//    }

    private final Map<CommandType, CommandHandler> handlers;

    public AsyncExecutor() {
        this.handlers = new HashMap<>();

        this.handlers.put(CommandType.HANDSHAKE, new HandshakeCommandHandler());
//        this.handlers.put(CommandType.AUTH, new Handler(AuthResult.class));
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
