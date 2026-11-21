package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.enums.CommandType;

public class HandshakeCommand implements Command {


    public HandshakeCommand() {}

    @Override
    public String toString() {
        return "HandshakeQuery{}";
    }

    @Override
    public CommandType getQueryType() {
        return CommandType.HANDSHAKE;
    }
}
