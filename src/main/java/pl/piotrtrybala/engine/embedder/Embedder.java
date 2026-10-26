package pl.piotrtrybala.engine.embedder;

public interface Embedder {
    float[] create(byte[] data);
    float[] create(char[] data);
}
