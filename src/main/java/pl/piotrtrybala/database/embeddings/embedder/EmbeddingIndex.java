package pl.piotrtrybala.database.embeddings.embedder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EmbeddingIndex {

    void add(int id, float[] vector);
    void addBatch(Map<Integer, float[]> batch);
    List<IndexSearchResult> search(float[] queryVector, int k);

    void save(String path) throws IOException;
    void load(String path) throws IOException;

    class IndexSearchResult {
        private final int id;
        private final float score;

        public IndexSearchResult(int id, float score) {
            this.id = id;
            this.score = score;
        }

        public int getId() {
            return id;
        }

        public float getScore() {
            return score;
        }
    }

}
