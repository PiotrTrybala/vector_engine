package pl.piotrtrybala.engine.query;

import java.lang.reflect.Array;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;

public class Parser {

    public Parser() {
    }

    public List<Query> parse(char[] data) {
        List<Query> queries = new ArrayList<>();

        CharBuffer buffer = CharBuffer.wrap(data);

        // 1. parse lines
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

        for (String line : lines) {



        }

        return queries;
    }

    public boolean isHandshake(String line) throws Exception {

        return false;
    }

}
