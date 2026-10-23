package pl.piotrtrybala.engine.query;

import pl.piotrtrybala.engine.query.types.*;

import java.lang.reflect.Array;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;

public class Parser {

    public Parser() {
    }

    public List<Query> parse(char[] data) throws Exception {
        List<Query> queries = new ArrayList<>();

        CharBuffer buffer = CharBuffer.wrap(data);

        // 1. read lines
        StringBuilder builder = new StringBuilder();
        List<String> lines = new ArrayList<>();

        while (buffer.hasRemaining()) {
            char c = buffer.get();
            if (c == '\n') continue;
            if (c == ';') {
                lines.add(builder.toString());
                builder.setLength(0);
            } else {
                builder.append(c);
            }
        }

        for (String query : lines) {
            if (QueryMatcher.isHandshakeQuery(query)) {
                queries.add(new HandshakeQuery());
            }
            if (QueryMatcher.isAuthQuery(query)) {
                queries.add(new AuthQuery(query));
            }
            if (QueryMatcher.isCreateCollectionQuery(query)) {
                queries.add(new CreateCollectionQuery(query));
            }
            if (QueryMatcher.isInsertQuery(query)) {
                queries.add(new InsertQuery(query));
            }
            if (QueryMatcher.isUpdateQuery(query)) {
                queries.add(new UpdateQuery(query));
            }
        }

        return queries;
    }

}
