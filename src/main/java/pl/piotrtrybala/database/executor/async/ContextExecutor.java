package pl.piotrtrybala.database.executor.async;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.enums.CommandType;
import pl.piotrtrybala.database.executor.ContextResult;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.sync.Payload;
import pl.piotrtrybala.database.executor.async.results.AuthResult;
import pl.piotrtrybala.database.executor.async.results.HandshakeResult;
import pl.piotrtrybala.database.executor.enums.ExecutorError;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ContextExecutor implements Executor<ContextResult> {

    private static final Logger LOGGER = LogManager.getLogger(ContextExecutor.class.getName());

    private static class Handler {
        private final Class<? extends ContextResult> result;

        public Handler(Class<? extends ContextResult> result) { this.result = result; }

        public ContextResult dispatch(Command command) {
            try {
                return result.getConstructor(command.getClass()).newInstance(command);
            } catch(InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                }
                return new Error(ExecutorError.INVALID_RESULT_TYPE);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                LOGGER.error("error with dispatching {}: {}", command.getQueryType().toString(), e.getMessage());
                return new Error(ExecutorError.INVALID_RESULT_TYPE);
            }
        }
    }

    private final Map<CommandType, Handler> handlers;

    public ContextExecutor() {
        this.handlers = new HashMap<>();

        this.handlers.put(CommandType.HANDSHAKE, new Handler(HandshakeResult.class));
        this.handlers.put(CommandType.AUTH, new Handler(AuthResult.class));
    }

    @Override
    public ContextResult execute(Payload payload) {
        LOGGER.info("processing query...");
        Command command = payload.getCommand();

        LOGGER.info("dispatching {}", command.getQueryType().toString());

        ContextExecutor.Handler handler = this.handlers.get(command.getQueryType());
        if (handler == null) {
            return null;
        }

        return handler.dispatch(command);
    }
}
