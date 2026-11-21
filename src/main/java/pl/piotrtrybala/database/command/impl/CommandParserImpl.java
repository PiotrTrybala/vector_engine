package pl.piotrtrybala.database.command.impl;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.CommandHandler;
import pl.piotrtrybala.database.command.Parser;
import pl.piotrtrybala.database.command.handlers.*;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class CommandParserImpl implements Parser<Command> {

    private final List<CommandHandler> handlers;

    public CommandParserImpl() {
        this.handlers = List.of(
                new AuthCommandHandler(),
                new CreateCommandHandler(),
                new HandshakeCommandHandler(),
                new InsertCommandHandler(),
                new SelectCommandHandler(),
                new ShowCommandHandler(),
                new UpdateCommandHandler(),
                new UseCommandHandler(),
                new PasswordCommandHandler()
        );
    }

    private List<String> makeLines(char[] data) {
                StringBuilder builder = new StringBuilder();
        List<String> queries = new ArrayList<>();

        CharBuffer buffer = CharBuffer.wrap(data);
        while (buffer.hasRemaining()) {
            char c = buffer.get();
            if (c == '\n') continue;
            if (c == ';') {
                queries.add(builder.toString());
                builder.setLength(0);
            } else {
                builder.append(c);
            }
        }
        return queries;
    }

    @Override
    public List<Command> parse(char[] data) throws CommandException {
        List<Command> cmds = new ArrayList<>();

        List<String> rawCmds = this.makeLines(data);
        if (rawCmds.isEmpty()) {
            throw new CommandException("invalid command data: " + new String(data));
        }

        for (String rawCmd : rawCmds) {
            boolean matchedCmdHandler = false;

            for (CommandHandler handler : handlers) {
                if (handler.canParse(rawCmd)) {
                    cmds.add(handler.parse(rawCmd));
                    matchedCmdHandler = true;
                    break;
                }
            }
            if (!matchedCmdHandler) {
                throw new CommandException("unknown command: " + rawCmd);
            }
        }

        return cmds;
    }
}
