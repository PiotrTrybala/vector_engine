package pl.piotrtrybala.engine.query;

import pl.piotrtrybala.engine.query.enums.Keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryMatcher {

    public static List<String> preprocess(String query) {
        List<String> tokens = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return tokens;

//        String regex = "`[^`]*`|\\[[^\\]]*\\]|=|[^\\s,;=]+";
        String regex = "`[^`]*`|\\[[^\\]]*\\]|=|\\(|\\)|[^\\s,;=()]+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public static boolean isHandshakeQuery(String query) throws Exception {
        List<String> tokens = QueryMatcher.preprocess(query);
        return tokens.size() == 1 && Keyword.match(tokens.getFirst(), Keyword.HANDSHAKE);
    }

    public static boolean isAuthQuery(String query) throws Exception {
        List<String> tokens = QueryMatcher.preprocess(query);
        return tokens.size() == 3 && Keyword.match(tokens.getFirst(), Keyword.AUTH);
    }

    public static boolean isCreateCollectionQuery(String query) throws Exception {
        List<String> tokens = QueryMatcher.preprocess(query);
        return tokens.size() == 3 && Keyword.match(tokens.getFirst(), Keyword.CREATE) && Keyword.match(tokens.get(1), Keyword.COLLECTION);
    }

    public static boolean isInsertQuery(String query) throws Exception {
        List<String> tokens = QueryMatcher.preprocess(query);
        return tokens.size() == 8 && Keyword.match(tokens.getFirst(), Keyword.INSERT)
                && Keyword.match(tokens.get(1), Keyword.INTO)
                && Keyword.match(tokens.get(3), Keyword.TYPE)
                && Keyword.match(tokens.get(5), Keyword.BEGIN)
                && Keyword.match(tokens.get(7), Keyword.END);
    }

    public static boolean isUpdateQuery(String query) throws Exception {
        List<String> tokens = QueryMatcher.preprocess(query);

        if (!Keyword.match(tokens.getFirst(), Keyword.UPDATE)) return false;
        if (!Keyword.match(tokens.get(2), Keyword.VALUE)) return false;

        int whereIndex = -1;
        for (int i = 2; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.WHERE)) {
                if (whereIndex == -1) {
                    whereIndex = i;
                } else {
                    throw new Exception("duplicate where clause");
                }
            }
        }

        return whereIndex != -1;
    }

    public static boolean isSelectQuery(String query) throws Exception {
        List<String> tokens = QueryMatcher.preprocess(query);

        int selectIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.SELECT)) {
                selectIndex = i;
                break;
            }
        }
        
        return selectIndex == 0 && tokens.size() >= 4;
    }

}
