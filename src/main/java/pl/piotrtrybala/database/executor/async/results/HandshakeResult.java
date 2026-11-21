package pl.piotrtrybala.database.executor.async.results;

import pl.piotrtrybala.database.command.types.HandshakeCommand;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;

public class HandshakeResult implements AsyncResult {

    public HandshakeResult() {}

    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
