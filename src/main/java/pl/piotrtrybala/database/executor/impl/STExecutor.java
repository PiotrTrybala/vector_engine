package pl.piotrtrybala.database.executor.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.enums.ExecutorErrorType;
import pl.piotrtrybala.database.executor.impl.result.*;
import pl.piotrtrybala.database.executor.types.ExecutorError;
import pl.piotrtrybala.database.executor.types.ExecutorPayload;
import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.enums.QueryType;
import pl.piotrtrybala.database.query.types.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class STExecutor implements Executor {

    private static class Handler {

        Class<? extends ExecutorResult> resultType;

        public Handler(Class<? extends ExecutorResult> result) {
            this.resultType = result;
        }

        public ExecutorResult dispatch(Query query) {
            try {
                return resultType.getConstructor(query.getClass()).newInstance(query);
            } catch(InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                }
                return new ExecutorError(ExecutorErrorType.INVALID_RESULT_TYPE);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                LOGGER.error("error with dispatching {}: {}", query.getQueryType().toString(), e.getMessage());
                return new ExecutorError(ExecutorErrorType.INVALID_RESULT_TYPE);
            }
        }

    }

    private static final Logger LOGGER = LogManager.getLogger(STExecutor.class.getName());

    private final Map<QueryType, Handler> queryHandlers;

    public STExecutor() {
        this.queryHandlers = new HashMap<>();

        this.queryHandlers.put(QueryType.HANDSHAKE, new Handler(HandshakeResult.class));
        this.queryHandlers.put(QueryType.AUTH, new Handler(AuthResult.class));
        this.queryHandlers.put(QueryType.CREATE_COLLECTION, new Handler(CreateCollectionResult.class));
        this.queryHandlers.put(QueryType.USE_COLLECTION, new Handler(UseCollectionResult.class));
        this.queryHandlers.put(QueryType.SHOW, new Handler(ShowResult.class));
    }

    @Override
    public ExecutorResult process(ExecutorPayload payload) {
        LOGGER.info("processing query...");
        Query query = payload.getQuery();

        LOGGER.info("dispatching {}", query.getQueryType().toString());

        Handler handler = this.queryHandlers.get(query.getQueryType());
        if (handler == null) {
            return null;
        }

        return handler.dispatch(query);
    }
}
