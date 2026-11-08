package pl.piotrtrybala.database.query;

import pl.piotrtrybala.database.query.enums.Keyword;
import pl.piotrtrybala.database.query.enums.MetadataValueType;
import pl.piotrtrybala.database.query.enums.SelectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryUtils {

    public static int DEFAULT_LIMIT = 5;

    public static char[] parseEmbeddingData(List<String> tokens) throws QueryException {

        int dataIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.BEGIN)) dataIndex = i;
        }

        if (dataIndex + 1 > tokens.size()) throw new QueryException("invalid expression");

        String data = tokens.get(dataIndex + 1);

        if (data.startsWith("`") && data.endsWith("`")) {
            return data.substring(1, data.length() - 1).toCharArray();
        }

        throw new QueryException("invalid data expression");
    }

    public static HashMap<String, Object> parseMetadata(List<String> tokens) throws QueryException {
        HashMap<String, Object> metadata = new HashMap<>();

        int metadataIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.VALUE)) metadataIndex = i;
        }

        if (metadataIndex + 1 > tokens.size()) throw new QueryException("invalid expression");

        String data = tokens.get(metadataIndex + 1);

        System.out.println("metadata tokens = " + data);

        if (!data.startsWith("[") || !data.endsWith("]")) {
            throw new QueryException("invalid open expression");
        }

        List<String> kvPairs = Arrays.stream(data.substring(1, data.length() - 1)
                .split(",")).toList();

        for (String kvPair : kvPairs) {

            String[] kv = kvPair.split(":");

            String key = kv[0].trim();
            String rawValue = kv[1].trim();

            String[] valueParts = rawValue.split(" ");
            if (valueParts.length != 3) throw new QueryException("invalid value expression: " + rawValue);

            if (!Keyword.match(valueParts[1], Keyword.AS)) {
                throw new QueryException("invalid keyword: " + valueParts[1]);
            }

            MetadataValueType valueType = null;
            try {
                valueType = MetadataValueType.valueOf(valueParts[2].toUpperCase());
            } catch (Exception e) {
                throw new QueryException("invalid value type: " + valueParts[2]);
            }

            String value = valueParts[0].trim();

            switch (valueType) {
                case INT:
                    try {
                        metadata.put(key, Integer.parseInt(value));
                    } catch (Exception e) {
                        throw new QueryException("invalid integer value: " + value);
                    }
                    break;
                case DOUBLE:
                    try {
                        metadata.put(key, Double.parseDouble(value));
                    } catch (Exception e) {
                        throw new QueryException("invalid double value: " + value);
                    }
                    break;
                case TEXT:

                    if (!value.startsWith("\"") || !value.endsWith("\"")) {
                        throw new QueryException("invalid text value: " + value);
                    }
                    String text = value.substring(1, value.length() - 1);
                    metadata.put(key, text);
                    break;
                case BOOLEAN:
                    try {
                        metadata.put(key, Boolean.parseBoolean(value));
                    } catch (Exception e) {
                        throw new QueryException("invalid boolean value: " + value);
                    }
                    break;
            }
        }
        return metadata;
    }

    public static SelectType parseSelectType(List<String> tokens) throws QueryException {
        try {
            return SelectType.valueOf(tokens.get(1).toUpperCase().trim());
        } catch (Exception e) {
            throw new QueryException("invalid select type: " + tokens.get(1));
        }
    }

    public static List<String> parseMetadataKeys(List<String> tokens) throws QueryException {
        int metadataIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.METADATA)) {
                metadataIndex = i;
                break;
            }
        }

        if (metadataIndex + 1 >= tokens.size() || metadataIndex == -1) {
            throw new QueryException("invalid metadata select");
        }

        String keys = tokens.get(metadataIndex + 1);

        if (!keys.startsWith("[") || !keys.endsWith("]")) {
            throw new QueryException("invalid metadata definition: not closed");
        }

        return Arrays.stream(keys.substring(1, keys.length() - 1)
                .replace(" ", "")
                .split(",")).map(String::trim)
                .toList();
    }

    public static String parseCollectionName(List<String> tokens) throws QueryException {
        int fromIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.FROM)) {
                fromIndex = i;
                break;
            }
        }

        if (fromIndex + 1 >= tokens.size() || fromIndex == -1) {
            throw new QueryException("invalid from definition");
        }

        return tokens.get(fromIndex + 1).trim();
    }

    public static List<String> retrieveExpressionTokens(List<String> tokens) throws QueryException {
        List<String> exprTokens = new ArrayList<>();

        boolean isWhereClause = false;

        for (String token : tokens) {
            if (Keyword.is(token, Keyword.WHERE)) {
                isWhereClause = true;
                continue;
            }

            if (Keyword.is(token, Keyword.LIMIT)) {
                break;
            }

            if (isWhereClause) {
                exprTokens.add(token);
            }
        }

        return exprTokens;
    }

    public static int parseLimit(List<String> tokens) throws QueryException {

        int limitIndex = -1;

        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.LIMIT)) {
                limitIndex = i;
                break;
            }
        }

        if (limitIndex + 1 >= tokens.size() || limitIndex == -1) {
            throw new QueryException("invalid limit definition");
        }

        String limitToken = tokens.get(limitIndex + 1);

        try {
            return Integer.parseInt(limitToken.trim());
        } catch(NumberFormatException e) {
            return DEFAULT_LIMIT;
        }
    }

    public static List<String> skipUntilKeyword(List<String> tokens, Keyword keyword) {
        List<String> output = new ArrayList<>();
        boolean canAdd = false;
        for (String s : tokens) {
            if (s.equals(keyword.getKeyword())) {
                canAdd = true;
                continue;
            }

            if (canAdd) output.add(s);
        }
        return output;
    }

    public static List<String> preprocess(String query) {
        List<String> tokens = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return tokens;

        String regex = "`[^`]*`|\\[[^\\]]*\\]|=|\\(|\\)|[^\\s,;=()]+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public static int findKeywordIndex(List<String> tokens, Keyword keyword) {
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.WHERE)) {
                return i;
            }
        }
        return -1;
    }
}
