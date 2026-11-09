package pl.piotrtrybala.database.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MetadataUtils {

    private static final Gson GSON = new Gson();

    public static HashMap<String, Object> parse(byte[] metadata) {
        if (metadata == null || metadata.length == 0) {
            return new HashMap<>();
        }

        String jsonString = new String(metadata, StandardCharsets.UTF_8);
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        Map<String, Object> parsedMap = GSON.fromJson(jsonString, type);

        return parsedMap instanceof HashMap ? (HashMap<String, Object>) parsedMap : new HashMap<>(parsedMap);
    }

    public static byte[] save(HashMap<String, Object> metadata) {
        if (metadata == null) {
            return new byte[0];
        }

        String jsonString = GSON.toJson(metadata);
        return jsonString.getBytes(StandardCharsets.UTF_8);
    }

}
