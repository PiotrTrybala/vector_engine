package pl.piotrtrybala.database.embeddings.embedder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EmbeddingIndex {

    void add(int id, float[] vector);
    void addBatch(Map<Integer, float[]> batch);
    List<IndexSearchResult> search(float[] queryVector, int k);

    void save(String path) throws IOException;

    record IndexSearchResult(int id, float score) {
    }
}
