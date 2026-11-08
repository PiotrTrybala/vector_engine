package pl.piotrtrybala.database.query;

import pl.piotrtrybala.database.query.types.*;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

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

        if (lines.isEmpty()) {
            throw new Exception("no separator found");
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
            if (QueryMatcher.isSelectQuery(query)) {
                queries.add(new SelectQuery(query));
            }
            if(QueryMatcher.isShowQuery(query)) {
                queries.add(new ShowQuery(query));
            }

            if (QueryMatcher.isUseQuery(query)) {
                queries.add(new UseCollectionQuery(query));
            }
        }

        return queries;
    }

}
