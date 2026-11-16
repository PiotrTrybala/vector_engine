package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.types.SelectQuery;

public class SelectResult implements Result {

    private final SelectQuery command;

    public SelectResult(SelectQuery command) {
        this.command = command;
    }

    @Override
    public char[] getResult() {



        return "OK".toCharArray();
    }
}
