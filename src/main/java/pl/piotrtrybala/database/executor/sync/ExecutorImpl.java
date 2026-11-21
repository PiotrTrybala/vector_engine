package pl.piotrtrybala.database.executor.sync;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.sync.results.*;
import pl.piotrtrybala.database.executor.enums.ExecutorError;
import pl.piotrtrybala.database.executor.Result;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.enums.CommandType;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ExecutorImpl implements Executor<Result> {

    private static class Handler {

        private final Class<? extends Result> resultType;

        public Handler(Class<? extends Result> result) {
            this.resultType = result;
        }

        public Result dispatch(Command command) {
            try {
                return resultType.getConstructor(command.getClass()).newInstance(command);
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

    private static final Logger LOGGER = LogManager.getLogger(ExecutorImpl.class.getName());

    private final Map<CommandType, Handler> queryHandlers;

    public ExecutorImpl() {
        this.queryHandlers = new HashMap<>();

        this.queryHandlers.put(CommandType.HANDSHAKE, new Handler(HandshakeResult.class));
        this.queryHandlers.put(CommandType.AUTH, new Handler(AuthResult.class));
        this.queryHandlers.put(CommandType.CREATE_COLLECTION, new Handler(CreateResult.class));
        this.queryHandlers.put(CommandType.USE_COLLECTION, new Handler(UseResult.class));
        this.queryHandlers.put(CommandType.SHOW, new Handler(ShowResult.class));
        this.queryHandlers.put(CommandType.INSERT, new Handler(InsertResult.class));
        this.queryHandlers.put(CommandType.PASSWORD, new Handler(PasswordResult.class));
        this.queryHandlers.put(CommandType.UPDATE, new Handler(UpdateResult.class));
    }

    @Override
    public Result execute(Payload payload) {
        LOGGER.info("processing query...");
        Command command = payload.getCommand();

        LOGGER.info("dispatching {}", command.getQueryType().toString());

        Handler handler = this.queryHandlers.get(command.getQueryType());
        if (handler == null) {
            return null;
        }

        return handler.dispatch(command);
    }
}
