package pl.piotrtrybala.engine.executor.impl.result;

import pl.piotrtrybala.engine.executor.types.ExecutorResult;
import pl.piotrtrybala.engine.query.types.CreateCollectionQuery;

public class CreateCollectionResult implements ExecutorResult {

    private final CreateCollectionQuery query;

    public CreateCollectionResult(CreateCollectionQuery query) {
        this.query = query;
    }

    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
