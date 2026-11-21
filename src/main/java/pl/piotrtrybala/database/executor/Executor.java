package pl.piotrtrybala.database.executor;

import pl.piotrtrybala.database.executor.sync.Payload;

public interface Executor<T> {
    T execute(Payload payload);
}
