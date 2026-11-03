package pl.piotrtrybala.engine.executor.impl.result;

import pl.piotrtrybala.engine.executor.types.ExecutorResult;
import pl.piotrtrybala.engine.query.types.UseCollectionQuery;

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
