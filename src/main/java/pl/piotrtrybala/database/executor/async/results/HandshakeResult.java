package pl.piotrtrybala.database.executor.async.results;

import pl.piotrtrybala.database.command.types.HandshakeCommand;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.ContextResult;

public class HandshakeResult implements ContextResult {

    public HandshakeResult(HandshakeCommand command) {}

    @Override
    public char[] getResult(Context context) {
        return "OK".toCharArray();
    }
}
