package pl.piotrtrybala.engine.query;

import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QueryUtils {


    public static char[] parseEmbeddingData(List<String> parts) throws Exception {

        int dataIndex = -1;
        for (int i = 0; i < parts.size(); i++) {
            if (Keyword.is(parts.get(i), Keyword.BEGIN)) dataIndex = i;
        }

        if (dataIndex + 1 > parts.size()) throw new Exception("invalid expression");

        String data = parts.get(dataIndex + 1);

        if (data.startsWith("`") && data.endsWith("`")) {
            return data.substring(1, data.length() - 1).toCharArray();
        }
        throw new Exception("invalid data expression");
    }

    public static HashMap<String, Object> parseMetadata(List<String> parts) throws Exception {
        HashMap<String, Object> metadata = new HashMap<>();

        int metadataIndex = -1;
        for (int i = 0; i < parts.size(); i++) {
            if (Keyword.is(parts.get(i), Keyword.VALUE)) metadataIndex = i;
        }

        if (metadataIndex + 1 > parts.size()) throw new Exception("invalid expression");

        String data = parts.get(metadataIndex + 1);

        System.out.println("metadata parts = " + data);

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

            MetadataType valueType = null;
            try {
                valueType = MetadataType.valueOf(valueParts[2].toUpperCase());
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

    public static WhereClause parseWhereClause(List<String> parts) {
        return new WhereClause();
    }

}
