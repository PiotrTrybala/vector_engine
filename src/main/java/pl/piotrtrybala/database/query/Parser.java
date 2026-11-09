package pl.piotrtrybala.database.query;

import pl.piotrtrybala.database.query.handlers.*;
import pl.piotrtrybala.database.query.types.*;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {

    private final List<QueryHandler> handlers;

    public Parser() {
        this.handlers = List.of(
                new AuthQueryHandler(),
                new CreateCollectionQueryHandler(),
                new HandshakeQueryHandler(),
                new InsertQueryHandler(),
                new SelectQueryHandler(),
                new ShowQueryHandler(),
                new UpdateQueryHandler(),
                new UseCollectionQueryHandler()
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

    public List<Query> parse(char[] data) throws QueryException {
        List<Query> queries = new ArrayList<>();

        List<String> rawQueries = this.parseBuffer(data);
        if (rawQueries.isEmpty()) {
            throw new QueryException("invalid queries");
        }

        for (String query : rawQueries) {
            boolean matched = false;
            for (QueryHandler handler : this.handlers) {
                if (handler.canParse(query)) {
                    queries.add(handler.parse(query));
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                throw new QueryException("unknown query: " + query);
            }
        }

        return queries;
    }

}
