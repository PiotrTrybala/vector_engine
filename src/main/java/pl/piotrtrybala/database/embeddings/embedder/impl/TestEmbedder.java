package pl.piotrtrybala.database.embeddings.embedder.impl;

import pl.piotrtrybala.database.Entrypoint;
import pl.piotrtrybala.database.embeddings.embedder.Embedder;

import java.util.Random;

public class TestEmbedder implements Embedder {

    private final Random rand;
    private final int embeddingDimensions;

    public TestEmbedder(int embeddingsDimension) {
        this.rand = new Random(123);
        this.embeddingDimensions = embeddingsDimension;
    }

    @Override
    public float[] create(byte[] data) {
        float[] embedding = new float[embeddingDimensions];
        for (int i = 0; i < embeddingDimensions; i++) {
            embedding[i] = (2 * rand.nextFloat()) - 1; // -1 to 1
        }
        return embedding;
    }
}
