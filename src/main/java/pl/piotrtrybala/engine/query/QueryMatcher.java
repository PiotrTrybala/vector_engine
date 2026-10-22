package pl.piotrtrybala.engine.query;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryMatcher {

    public static List<String> preprocess(String query) {
        List<String> parts = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return parts;

        String regex = "`[^`]*`|\\[[^\\]]*\\]|=|[^\\s,;=]+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            parts.add(matcher.group());
        }



        return parts;
    }

    public static boolean isHandshakeQuery(String query) throws Exception {
        List<String> parts = QueryMatcher.preprocess(query);
        return parts.size() == 1 && Keyword.match(parts.getFirst(), Keyword.HANDSHAKE);
    }

    public static boolean isAuthQuery(String query) throws Exception {
        List<String> parts = QueryMatcher.preprocess(query);
        return parts.size() == 3 && Keyword.match(parts.getFirst(), Keyword.AUTH);
    }

    public static boolean isCreateCollectionQuery(String query) throws Exception {
        List<String> parts = QueryMatcher.preprocess(query);
        return parts.size() == 3 && Keyword.match(parts.getFirst(), Keyword.CREATE) && Keyword.match(parts.get(1), Keyword.COLLECTION);
    }

    public static boolean isInsertQuery(String query) throws Exception {
        List<String> parts = QueryMatcher.preprocess(query);
        System.out.println("insert query parts = " + parts);
        return parts.size() == 8 && Keyword.match(parts.getFirst(), Keyword.INSERT)
                && Keyword.match(parts.get(1), Keyword.INTO)
                && Keyword.match(parts.get(3), Keyword.TYPE)
                && Keyword.match(parts.get(5), Keyword.BEGIN)
                && Keyword.match(parts.get(7), Keyword.END);
    }


}
