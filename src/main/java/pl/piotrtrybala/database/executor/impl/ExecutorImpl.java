package pl.piotrtrybala.database.executor.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.enums.ExecutorErrorType;
import pl.piotrtrybala.database.executor.impl.result.*;
import pl.piotrtrybala.database.executor.types.Error;
import pl.piotrtrybala.database.executor.types.Payload;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ExecutorImpl implements Executor {

    private static class Handler {

        private final Class<? extends Result> resultType;

        public Handler(Class<? extends Result> result) {
            this.resultType = result;
        }

        public Result dispatch(Command query) {
            try {
                return resultType.getConstructor(query.getClass()).newInstance(query);
            } catch(InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                }
                return new Error(ExecutorErrorType.INVALID_RESULT_TYPE);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                LOGGER.error("error with dispatching {}: {}", query.getQueryType().toString(), e.getMessage());
                return new Error(ExecutorErrorType.INVALID_RESULT_TYPE);
            }
        }

    }

    private static final Logger LOGGER = LogManager.getLogger(ExecutorImpl.class.getName());

    private final Map<QueryType, Handler> queryHandlers;

    public ExecutorImpl() {
        this.queryHandlers = new HashMap<>();

        this.queryHandlers.put(QueryType.HANDSHAKE, new Handler(HandshakeResult.class));
        this.queryHandlers.put(QueryType.AUTH, new Handler(AuthResult.class));
        this.queryHandlers.put(QueryType.CREATE_COLLECTION, new Handler(CreateResult.class));
        this.queryHandlers.put(QueryType.USE_COLLECTION, new Handler(UseResult.class));
        this.queryHandlers.put(QueryType.SHOW, new Handler(ShowResult.class));
        this.queryHandlers.put(QueryType.INSERT, new Handler(InsertResult.class));
        this.queryHandlers.put(QueryType.PASSWORD, new Handler(PasswordResult.class));
    }

    @Override
    public Result process(Payload payload) {
        LOGGER.info("processing query...");
        Command query = payload.getQuery();

        LOGGER.info("dispatching {}", query.getQueryType().toString());

        Handler handler = this.queryHandlers.get(query.getQueryType());
        if (handler == null) {
            return null;
        }

        return handler.dispatch(query);
    }
}
