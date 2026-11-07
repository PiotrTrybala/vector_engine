package pl.piotrtrybala.database.executor;

import pl.piotrtrybala.database.executor.types.ExecutorPayload;
import pl.piotrtrybala.database.executor.types.ExecutorResult;

public interface Executor {

    ExecutorResult process(ExecutorPayload payload);

}
