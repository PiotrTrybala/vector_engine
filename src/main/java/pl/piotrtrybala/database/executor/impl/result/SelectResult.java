package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.types.SelectCommand;

public class SelectResult implements Result {

    private final SelectCommand command;

    public SelectResult(SelectCommand command) {
        this.command = command;
    }

    @Override
    public char[] getResult() {



        return "OK".toCharArray();
    }
}
