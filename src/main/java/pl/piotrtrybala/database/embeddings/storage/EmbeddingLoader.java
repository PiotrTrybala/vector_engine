package pl.piotrtrybala.database.embeddings.storage;

import java.io.IOException;

public interface EmbeddingLoader {

    void load(EmbeddingIndex index, String path, int dimensions, int totalVectors) throws IOException;

}
