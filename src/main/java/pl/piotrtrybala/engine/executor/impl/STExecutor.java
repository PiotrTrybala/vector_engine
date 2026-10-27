package pl.piotrtrybala.engine.executor.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.engine.executor.Executor;
import pl.piotrtrybala.engine.executor.enums.ExecutorErrorType;
import pl.piotrtrybala.engine.executor.impl.result.HandshakeResult;
import pl.piotrtrybala.engine.executor.types.ExecutorError;
import pl.piotrtrybala.engine.executor.types.ExecutorPayload;
import pl.piotrtrybala.engine.executor.types.ExecutorResult;
import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.enums.QueryType;
import pl.piotrtrybala.engine.query.types.HandshakeQuery;

import java.util.HashSet;
import java.util.List;

public class STExecutor implements Executor {

    private static final HashSet<QueryType> SUPPORTED_QUERY_TYPES = new HashSet<>(List.of(
            QueryType.HANDSHAKE
    ));
    private static final Logger logger = LogManager.getLogger();

    @Override
    public ExecutorResult process(ExecutorPayload payload) {

        logger.info("executing payload: {}", payload);
        Query query = payload.getQuery();

        logger.info("checking support for query...");
        if (!SUPPORTED_QUERY_TYPES.contains(query.getQueryType())) {
            logger.error("unsupported query type: {}", query.getQueryType().toString());
            return new ExecutorError(ExecutorErrorType.UNSUPPORTED_QUERY_TYPE);
        }

        if (query instanceof HandshakeQuery) {
            logger.info("dispatching: {}", query.getQueryType().toString());
            return this.dispatchHandshake((HandshakeQuery) query);
        }
        return null;
    }

    public ExecutorResult dispatchHandshake(HandshakeQuery query) {
        return new HandshakeResult();
    }
}
