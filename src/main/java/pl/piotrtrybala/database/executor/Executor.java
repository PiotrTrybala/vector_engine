package pl.piotrtrybala.database.executor;

import pl.piotrtrybala.database.executor.sync.Payload;

public interface Executor<T, P> {
    T execute(P payload);
}
