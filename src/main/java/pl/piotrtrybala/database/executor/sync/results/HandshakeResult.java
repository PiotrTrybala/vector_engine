package pl.piotrtrybala.database.executor.sync.results;

import pl.piotrtrybala.database.executor.Result;
import pl.piotrtrybala.database.command.types.HandshakeCommand;

public class HandshakeResult implements Result {

    public HandshakeResult(HandshakeCommand command) {
    }

    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }
}
