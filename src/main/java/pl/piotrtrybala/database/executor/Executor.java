package pl.piotrtrybala.database.executor;

import pl.piotrtrybala.database.executor.types.Payload;
import pl.piotrtrybala.database.executor.types.Result;

public interface Executor {
    Result process(Payload payload);
}
