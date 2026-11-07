package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.executor.types.ExecutorResult;

public class HandshakeResult implements ExecutorResult {
    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
