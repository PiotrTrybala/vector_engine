package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.types.HandshakeQuery;

public class HandshakeResult implements ExecutorResult {

    public HandshakeResult(HandshakeQuery query) {
    }

    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
