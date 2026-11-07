package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.types.UseCollectionQuery;

public class UseCollectionResult implements ExecutorResult {

    private final UseCollectionQuery query;

    public UseCollectionResult(UseCollectionQuery query) {
        this.query = query;
    }

    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
