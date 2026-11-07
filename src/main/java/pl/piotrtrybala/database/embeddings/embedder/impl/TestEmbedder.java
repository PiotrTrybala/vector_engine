package pl.piotrtrybala.database.embeddings.embedder.impl;

import pl.piotrtrybala.database.Entrypoint;
import pl.piotrtrybala.database.embeddings.embedder.Embedder;

import java.util.Random;

public class TestEmbedder implements Embedder {

    private final Random rand;
    private static final int EMBEDDING_SIZE = Entrypoint.getInstance()
            .getConfig()
            .getEmbedderConfig()
            .embeddingSize;

    public TestEmbedder() {
        this.rand = new Random();
    }

    public TestEmbedder(int seed) {
        this.rand = new Random(seed);
    }

    @Override
    public float[] create(byte[] data) {
        float[] embedding = new float[EMBEDDING_SIZE];
        for (int i = 0; i < EMBEDDING_SIZE; i++) {
            embedding[i] = (2 * rand.nextFloat()) - 1; // -1 to 1
        }
        return embedding;
    }
}
