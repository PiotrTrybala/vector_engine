package pl.piotrtrybala.database.query;

import pl.piotrtrybala.database.query.handlers.*;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<CommandHandler> handlers;

    public Parser() {
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

    private List<String> parseBuffer(char[] data) {
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

    public List<Command> parse(char[] data) throws CommandException {
        List<Command> queries = new ArrayList<>();

        List<String> rawQueries = this.parseBuffer(data);
        if (rawQueries.isEmpty()) {
            throw new CommandException("invalid queries");
        }

        for (String query : rawQueries) {
            boolean matched = false;
            for (CommandHandler handler : this.handlers) {
                if (handler.canParse(query)) {
                    queries.add(handler.parse(query));
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                throw new CommandException("unknown query: " + query);
            }
        }

        return queries;
    }

}
