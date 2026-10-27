package pl.piotrtrybala.engine.executor.impl.result;

import pl.piotrtrybala.engine.executor.types.ExecutorResult;

public class HandshakeResult implements ExecutorResult {
    @Override
    public String getResult() {
        return "OK";
    }
}
