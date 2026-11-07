package pl.piotrtrybala.database.embeddings.embedder;

public interface Embedder {
    float[] create(byte[] data);
}
