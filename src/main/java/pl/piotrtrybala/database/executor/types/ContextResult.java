package pl.piotrtrybala.database.executor.types;

import pl.piotrtrybala.database.engine.Context;

public interface ContextResult {
    char[] getResult(Context context);
}
