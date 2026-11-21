package pl.piotrtrybala.database.executor.blocking.result;

import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.types.ContextResult;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.command.types.HandshakeCommand;

public class HandshakeResult implements Result {

    public HandshakeResult(HandshakeCommand command) {
    }

    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
