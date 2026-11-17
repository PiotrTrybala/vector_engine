package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.command.types.HandshakeCommand;

public class HandshakeResult implements Result {

    public HandshakeResult(HandshakeCommand query) {
    }

    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
