package pl.piotrtrybala.database.command;

import pl.piotrtrybala.database.command.enums.Keyword;
import pl.piotrtrybala.database.command.enums.MetadataValueType;
import pl.piotrtrybala.database.command.enums.SelectType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandUtils {

    public static int DEFAULT_LIMIT = 5;
    public static int DEFAULT_OFFSET = 0;

    public static byte[] parseEmbeddingData(List<String> tokens) throws CommandException {

        int beginKeywordIndex = CommandUtils.findKeywordIndex(tokens, Keyword.BEGIN);
        int endKeywordIndex = CommandUtils.findKeywordIndex(tokens, Keyword.END);

        if (beginKeywordIndex == -1) {
            throw new CommandException("begin keyword was not found");
        }

        if (endKeywordIndex == -1) {
            throw new CommandException("end keyword was not found");
        }

        if (beginKeywordIndex > endKeywordIndex) {
            throw new CommandException("begin keyword must be before end keyword");
        }

        if (beginKeywordIndex + 1 > tokens.size()) {
            throw new CommandException("begin keyword at the end of command");
        }

        String data = tokens.get(beginKeywordIndex + 1);

        if (!data.startsWith("`") || !data.endsWith("`")) {
            throw new CommandException("invalid data definition");
        }

        return StandardCharsets.UTF_8.encode(data.substring(1, data.length() - 1)).array();
    }

    public static HashMap<String, Object> parseMetadata(List<String> tokens) throws CommandException {
        HashMap<String, Object> metadata = new HashMap<>();

        int metadataKeywordIndex = CommandUtils.findKeywordIndex(tokens, Keyword.VALUE);

        if (metadataKeywordIndex + 1 > tokens.size()) {
            throw new CommandException("value keyword at the end of command");
        }

        String data = tokens.get(metadataKeywordIndex + 1);

        if (!data.startsWith("[") || !data.endsWith("]")) {
            throw new CommandException("invalid open expression");
        }

        List<String> kvPairs = Arrays.stream(data.substring(1, data.length() - 1)
                .split(",")).toList();

        for (String kvPair : kvPairs) {

            String[] kv = kvPair.split(":");

            String key = kv[0].trim();
            String rawValue = kv[1].trim();

            String[] valueParts = rawValue.split(" ");
            if (valueParts.length != 3) {
                throw new CommandException("value definition is incorrectly defined: " + rawValue);
            }

            if (!Keyword.match(valueParts[1], Keyword.AS)) {
                throw new CommandException("invalid keyword: " + valueParts[1]);
            }

            MetadataValueType valueType = null;
            try {
                valueType = MetadataValueType.valueOf(valueParts[2].toUpperCase());
            } catch (Exception e) {
                throw new CommandException("undefined kv value type: " + valueParts[2]);
            }

            String value = valueParts[0].trim();

            switch (valueType) {
                case INT:
                    try {
                        metadata.put(key, Integer.parseInt(value));
                    } catch (Exception e) {
                        throw new CommandException("invalid integer value: " + value);
                    }
                    break;
                case DOUBLE:
                    try {
                        metadata.put(key, Double.parseDouble(value));
                    } catch (Exception e) {
                        throw new CommandException("invalid double value: " + value);
                    }
                    break;
                case TEXT:
                    if (!value.startsWith("\"") || !value.endsWith("\"")) {
                        throw new CommandException("invalid text value: " + value);
                    }
                    String text = value.substring(1, value.length() - 1);
                    metadata.put(key, text);
                    break;
                case BOOLEAN:
                    try {
                        metadata.put(key, Boolean.parseBoolean(value));
                    } catch (Exception e) {
                        throw new CommandException("invalid boolean value: " + value);
                    }
                    break;
            }
        }
        return metadata;
    }

    public static SelectType parseSelectType(List<String> tokens) throws CommandException {
        try {
            return SelectType.valueOf(tokens.get(1).toUpperCase().trim());
        } catch (Exception e) {
            throw new CommandException("invalid select type: " + tokens.get(1));
        }
    }

    public static List<String> parseMetadataKeys(List<String> tokens) throws CommandException {

        int metadataKeywordIndex = CommandUtils.findKeywordIndex(tokens, Keyword.METADATA);

        if (metadataKeywordIndex == -1) {
            throw new CommandException("metadata keyword was not found");
        }

        if (metadataKeywordIndex + 1 >= tokens.size()) {
            throw new CommandException("metadata keyword at the of the command");
        }

        String keys = tokens.get(metadataKeywordIndex + 1);

        if (!keys.startsWith("[")) {
            throw new CommandException("incorrectly opened metadata");
        }

        if (!keys.endsWith("]")) {
            throw new CommandException("inccorectly closed metadata");
        }

        return Arrays.stream(keys.substring(1, keys.length() - 1)
                .replace(" ", "")
                .split(",")).map(String::trim)
                .toList();
    }

    public static String parseCollectionName(List<String> tokens) throws CommandException {
        int fromKeywordIndex = CommandUtils.findKeywordIndex(tokens, Keyword.FROM);

        if (fromKeywordIndex == -1) {
            throw new CommandException("from keyword was not found");
        }

        if (fromKeywordIndex + 1 >= tokens.size()) {
            throw new CommandException("from keyword at the end of command");
        }

        return tokens.get(fromKeywordIndex + 1).trim();
    }

    public static List<String> retrieveExpressionTokens(List<String> tokens) throws CommandException {
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

    public static int parseLimit(List<String> tokens) throws CommandException {

        int limitKeywordIndex = CommandUtils.findKeywordIndex(tokens, Keyword.LIMIT);

        if (limitKeywordIndex == -1) {
            return DEFAULT_LIMIT;
        }

        if (limitKeywordIndex + 1 >= tokens.size()) {
            throw new CommandException("limit keyword at the end of sequence");
        }

        String limitToken = tokens.get(limitKeywordIndex + 1);

        try {
            return Integer.parseInt(limitToken.trim());
        } catch(NumberFormatException e) {
            throw new CommandException("invalid limit value: " + limitToken.trim());
        }
    }

    public static int parseOffset(List<String> tokens) throws CommandException {

        int offsetKeywordIndex = CommandUtils.findKeywordIndex(tokens, Keyword.OFFSET);

        if (offsetKeywordIndex == -1) {
            return DEFAULT_OFFSET;
        }

        if (offsetKeywordIndex + 1 >= tokens.size()) {
            throw new CommandException("offset keyword at the end of sequence");
        }

        String offsetToken = tokens.get(offsetKeywordIndex + 1);

        try {
            return Integer.parseInt(offsetToken.trim());
        } catch(NumberFormatException e) {
            throw new CommandException("invalid offset value: " + offsetToken.trim());
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
            if (Keyword.is(tokens.get(i), keyword)) {
                return i;
            }
        }
        return -1;
    }
}
