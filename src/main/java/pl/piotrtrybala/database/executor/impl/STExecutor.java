package pl.piotrtrybala.database.executor.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.enums.ExecutorErrorType;
import pl.piotrtrybala.database.executor.impl.result.AuthResult;
import pl.piotrtrybala.database.executor.impl.result.CreateCollectionResult;
import pl.piotrtrybala.database.executor.impl.result.HandshakeResult;
import pl.piotrtrybala.database.executor.impl.result.UseCollectionResult;
import pl.piotrtrybala.database.executor.types.ExecutorError;
import pl.piotrtrybala.database.executor.types.ExecutorPayload;
import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.enums.QueryType;
import pl.piotrtrybala.database.query.types.*;

import java.util.HashSet;
import java.util.List;

public class STExecutor implements Executor {

    private static final HashSet<QueryType> SUPPORTED_QUERY_TYPES = new HashSet<>(List.of(
            QueryType.HANDSHAKE,
            QueryType.AUTH,
            QueryType.CREATE_COLLECTION,
            QueryType.USE,
            QueryType.SHOW
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

        logger.info("dispatching: {}", query.getQueryType().toString());

        if (query instanceof HandshakeQuery) {
            return dispatchHandshakeQuery((HandshakeQuery) query);
        }

        if (query instanceof AuthQuery) {
            return dispatchAuthQuery((AuthQuery) query);
        }

        if (query instanceof CreateCollectionQuery) {
            return dispatchCreateCollectionQuery((CreateCollectionQuery) query);
        }

        if (query instanceof UseCollectionQuery) {
            return dispatchUseCollectionQuery((UseCollectionQuery) query);
        }

        if (query instanceof ShowQuery) {

        }

        return null;
    }

    public ExecutorResult dispatchHandshakeQuery(HandshakeQuery query) {
        return new HandshakeResult();
    }

    public ExecutorResult dispatchAuthQuery(AuthQuery query) {
        return new AuthResult(query);
    }

    public ExecutorResult dispatchCreateCollectionQuery(CreateCollectionQuery query) {
        return new CreateCollectionResult(query);
    }

    public ExecutorResult dispatchUseCollectionQuery(UseCollectionQuery query) {
        return new UseCollectionResult(query);
    }

    public ExecutorResult dispatchShowQuery(ShowQuery query) {
        return new ShowResult(query);
    }
}
