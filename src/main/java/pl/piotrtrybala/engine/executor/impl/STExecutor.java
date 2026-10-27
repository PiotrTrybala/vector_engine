package pl.piotrtrybala.engine.executor.impl;

import pl.piotrtrybala.engine.executor.Executor;
import pl.piotrtrybala.engine.executor.impl.result.HandshakeResult;
import pl.piotrtrybala.engine.executor.types.ExecutorPayload;
import pl.piotrtrybala.engine.executor.types.ExecutorResult;
import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.types.HandshakeQuery;

public class STExecutor implements Executor {

    @Override
    public ExecutorResult process(ExecutorPayload payload) {

        Query query = payload.getQuery();

        if (query instanceof HandshakeQuery) {
            return this.dispatchHandshake((HandshakeQuery) query);
        }

        return null;
    }

    public ExecutorResult dispatchHandshake(HandshakeQuery query) {
        return new HandshakeResult();
    }
}
