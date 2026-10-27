package pl.piotrtrybala.engine.executor;

import pl.piotrtrybala.engine.executor.types.ExecutorPayload;
import pl.piotrtrybala.engine.executor.types.ExecutorResult;

public interface Executor {

    ExecutorResult process(ExecutorPayload payload);

}
