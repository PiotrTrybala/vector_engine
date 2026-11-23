package pl.piotrtrybala.database.executor.async.results;

import com.google.gson.Gson;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.util.HashMap;
import java.util.List;

public class SelectResult implements AsyncResult {

    private static final Gson GSON = new Gson();
    private final List<HashMap<String, Object>> embeddingMetadata;
    private final List<Embedding> embeddings;

    public SelectResult(List<HashMap<String, Object>> embeddingMetadata, List<Embedding> embeddings) {
        this.embeddingMetadata = embeddingMetadata;
        this.embeddings = embeddings;
    }

    @Override
    public char[] getResult() {

        if (embeddingMetadata != null) {
            String output = GSON.toJson(embeddingMetadata);
            return output.toCharArray();
        }

        if (embeddings != null) {
            String output = GSON.toJson(embeddings);
            return output.toCharArray();
        }



        return "OK".toCharArray();
    }
}
