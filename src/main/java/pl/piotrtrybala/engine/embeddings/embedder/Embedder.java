package pl.piotrtrybala.engine.embeddings.embedder;

public interface Embedder {
    float[] create(byte[] data);
    float[] create(char[] data);
}
