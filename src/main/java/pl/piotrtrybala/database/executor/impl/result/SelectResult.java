package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.executor.types.Result;

public class SelectResult implements Result {
    @Override
    public char[] getResult() {
        return new char[0];
    }
}
