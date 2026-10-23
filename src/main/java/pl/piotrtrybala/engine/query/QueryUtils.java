package pl.piotrtrybala.engine.query;

import pl.piotrtrybala.engine.query.enums.Keyword;
import pl.piotrtrybala.engine.query.enums.MetadataValueType;
import pl.piotrtrybala.engine.query.enums.SelectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QueryUtils {

    public static int DEFAULT_LIMIT = 5;

    public static char[] parseEmbeddingData(List<String> tokens) throws Exception {

        int dataIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.BEGIN)) dataIndex = i;
        }

        if (dataIndex + 1 > tokens.size()) throw new Exception("invalid expression");

        String data = tokens.get(dataIndex + 1);

        if (data.startsWith("`") && data.endsWith("`")) {
            return data.substring(1, data.length() - 1).toCharArray();
        }
        throw new Exception("invalid data expression");
    }

    public static HashMap<String, Object> parseMetadata(List<String> tokens) throws Exception {
        HashMap<String, Object> metadata = new HashMap<>();

        int metadataIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.VALUE)) metadataIndex = i;
        }

        if (metadataIndex + 1 > tokens.size()) throw new Exception("invalid expression");

        String data = tokens.get(metadataIndex + 1);

        System.out.println("metadata tokens = " + data);

        if (!data.startsWith("[") || !data.endsWith("]")) {
            throw new Exception("invalid open expression");
        }

        List<String> kvPairs = Arrays.stream(data.substring(1, data.length() - 1)
                .split(",")).toList();

        for (String kvPair : kvPairs) {

            String[] kv = kvPair.split(":");

            String key = kv[0].trim();
            String rawValue = kv[1].trim();

            String[] valueParts = rawValue.split(" ");
            if (valueParts.length != 3) throw new Exception("invalid value expression: " + rawValue);

            if (!Keyword.match(valueParts[1], Keyword.AS)) {
                throw new Exception("invalid keyword: " + valueParts[1]);
            }

            MetadataValueType valueType = null;
            try {
                valueType = MetadataValueType.valueOf(valueParts[2].toUpperCase());
            } catch (Exception e) {
                throw new Exception("invalid value type: " + valueParts[2]);
            }

            String value = valueParts[0].trim();

            switch (valueType) {
                case INT:
                    try {
                        metadata.put(key, Integer.parseInt(value));
                    } catch (Exception e) {
                        throw new Exception("invalid integer value: " + value);
                    }
                    break;
                case DOUBLE:
                    try {
                        metadata.put(key, Double.parseDouble(value));
                    } catch (Exception e) {
                        throw new Exception("invalid double value: " + value);
                    }
                    break;
                case TEXT:

                    if (!value.startsWith("\"") || !value.endsWith("\"")) {
                        throw new Exception("invalid text value: " + value);
                    }
                    String text = value.substring(1, value.length() - 1);
                    metadata.put(key, text);
                    break;
                case BOOLEAN:
                    try {
                        metadata.put(key, Boolean.parseBoolean(value));
                    } catch (Exception e) {
                        throw new Exception("invalid boolean value: " + value);
                    }
                    break;
            }
        }
        return metadata;
    }

    public static SelectType parseSelectType(List<String> tokens) throws Exception {
        try {
            return SelectType.valueOf(tokens.get(1).toUpperCase().trim());
        } catch (Exception e) {
            throw new Exception("invalid select type: " + tokens.get(1));
        }
    }

    public static List<String> parseMetadataKeys(List<String> tokens) throws Exception {

        int metadataIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println("tokens = " + tokens.get(i));
            if (Keyword.is(tokens.get(i), Keyword.METADATA)) {
                metadataIndex = i;
                break;
            }
        }

        System.out.println("where: " + tokens.get(metadataIndex) + ", " + tokens.get(metadataIndex + 1));

        if (metadataIndex + 1 >= tokens.size() || metadataIndex == -1) {
            throw new Exception("invalid metadata select");
        }

        String keys = tokens.get(metadataIndex + 1);

        if (!keys.startsWith("[") || !keys.endsWith("]")) {
            throw new Exception("invalid metadata definition: not closed");
        }

        return Arrays.stream(keys.substring(1, keys.length() - 1)
                .replace(" ", "")
                .split(",")).map(String::trim)
                .toList();
    }

    public static String parseCollectionName(List<String> tokens) throws Exception {
        int fromIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.FROM)) {
                fromIndex = i;
                break;
            }
        }

        if (fromIndex + 1 >= tokens.size() || fromIndex == -1) {
            throw new Exception("invalid from definition");
        }

        return tokens.get(fromIndex + 1).trim();
    }

    public static List<String> retrieveExpressionTokens(List<String> tokens) throws Exception {
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

    public static int parseLimit(List<String> tokens) throws Exception {

        int limitIndex = -1;

        for (int i = 0; i < tokens.size(); i++) {
            if (Keyword.is(tokens.get(i), Keyword.LIMIT)) {
                limitIndex = i;
                break;
            }
        }

        if (limitIndex + 1 >= tokens.size() || limitIndex == -1) {
            throw new Exception("invalid limit definition");
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
}
