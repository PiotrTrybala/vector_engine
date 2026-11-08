package pl.piotrtrybala.database.query;

import pl.piotrtrybala.database.query.enums.Keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryClassifier {

    public static boolean isHandshakeQuery(String query) throws QueryException {
        List<String> tokens = QueryUtils.preprocess(query);
        return tokens.size() == 1 && Keyword.match(tokens.getFirst(), Keyword.HANDSHAKE);
    }

    public static boolean isAuthQuery(String query) throws QueryException {
        List<String> tokens = QueryUtils.preprocess(query);
        return tokens.size() == 3 && Keyword.match(tokens.getFirst(), Keyword.AUTH);
    }

    public static boolean isCreateCollectionQuery(String query) throws QueryException {
        List<String> tokens = QueryUtils.preprocess(query);
        return tokens.size() == 3 && Keyword.match(tokens.getFirst(), Keyword.CREATE) && Keyword.match(tokens.get(1), Keyword.COLLECTION);
    }

    public static boolean isInsertQuery(String query) throws QueryException {
        List<String> tokens = QueryUtils.preprocess(query);
        return tokens.size() == 8 && Keyword.match(tokens.getFirst(), Keyword.INSERT)
                && Keyword.match(tokens.get(1), Keyword.INTO)
                && Keyword.match(tokens.get(3), Keyword.TYPE)
                && Keyword.match(tokens.get(5), Keyword.BEGIN)
                && Keyword.match(tokens.get(7), Keyword.END);
    }

    public static boolean isUpdateQuery(String query) throws QueryException {
        List<String> tokens = QueryUtils.preprocess(query);

        if (!Keyword.match(tokens.getFirst(), Keyword.UPDATE)) return false;
        if (!Keyword.match(tokens.get(2), Keyword.VALUE)) return false;

//        int whereIndex = -1;
//        for (int i = 2; i < tokens.size(); i++) {
//            if (Keyword.is(tokens.get(i), Keyword.WHERE)) {
//                if (whereIndex == -1) {
//                    whereIndex = i;
//                }
//            }
//        }
        int whereIndex = QueryUtils.findKeywordIndex(tokens, Keyword.WHERE);

        return whereIndex != -1;
    }

    public static boolean isSelectQuery(String query) throws QueryException {
        List<String> tokens = QueryUtils.preprocess(query);

//        int selectIndex = -1;
//        for (int i = 0; i < tokens.size(); i++) {
//            if (Keyword.is(tokens.get(i), Keyword.SELECT)) {
//                selectIndex = i;
//                break;
//            }
//        }
        int selectIndex = QueryUtils.findKeywordIndex(tokens, Keyword.SELECT);

        return selectIndex == 0 && tokens.size() >= 4;
    }

    public static boolean isShowQuery(String query) throws QueryException {
        List<String> tokens = QueryUtils.preprocess(query);
        return Keyword.match(tokens.getFirst(), Keyword.SHOW) && tokens.size() == 2;
    }

    public static boolean isUseQuery(String query) throws QueryException {
        List<String> tokens = QueryUtils.preprocess(query);
        return Keyword.match(tokens.getFirst(), Keyword.USE) && tokens.size() == 2;
    }

}
