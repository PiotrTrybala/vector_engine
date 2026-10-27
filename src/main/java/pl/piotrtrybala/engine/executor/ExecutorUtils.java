package pl.piotrtrybala.engine.executor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ExecutorUtils {

    public static byte[] toBytes(String str) {
        return StandardCharsets.UTF_8.encode(str).array();
    }

}
