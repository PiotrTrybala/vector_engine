package pl.piotrtrybala.engine.executor.impl.result;

import pl.piotrtrybala.engine.executor.ExecutorUtils;
import pl.piotrtrybala.engine.executor.types.ExecutorResult;

public class HandshakeResult implements ExecutorResult {
    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
