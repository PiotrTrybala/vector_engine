package pl.piotrtrybala.database.embeddings.storage.impl;

import com.github.jelmerk.hnswlib.core.DistanceFunction;
import com.github.jelmerk.hnswlib.core.DistanceFunctions;
import com.github.jelmerk.hnswlib.core.Item;
import com.github.jelmerk.hnswlib.core.hnsw.HnswIndex;
import jdk.jshell.spi.ExecutionControl;
import pl.piotrtrybala.database.Entrypoint;
import pl.piotrtrybala.database.embeddings.storage.EmbeddingIndex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmbeddingIndexImpl implements EmbeddingIndex {

    private static final int EMBEDDING_DIMENSIONS = Entrypoint.getInstance().getConfig().getEmbedderConfig().embeddingSize;

    static class FloatVectorItem implements Item<Integer, float[]> {
        private final int id;
        private final float[] vector;

        public FloatVectorItem(int id, float[] vector) {
            this.id = id;
            this.vector = vector;
        }

        @Override
        public Integer id() {
            return id;
        }

        @Override
        public float[] vector() {
            return vector;
        }

        @Override
        public int dimensions() {
            return EMBEDDING_DIMENSIONS;
        }
    }

    private final HnswIndex<Integer, float[], FloatVectorItem, Float> index;

    public EmbeddingIndexImpl(int maxElements, int dimensions, int m, int efConstruction) {
        this.index = HnswIndex.newBuilder(dimensions, DistanceFunctions.FLOAT_COSINE_DISTANCE, maxElements)
                .withM(m)
                .withEfConstruction(efConstruction)
                .build();
    }

    @Override
    public void add(int id, float[] vector) {
        FloatVectorItem item = new FloatVectorItem(id, vector);
        index.add(item);
    }

    @Override
    public void addBatch(Map<Integer, float[]> batch) {
        for (Map.Entry<Integer, float[]> entry : batch.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public List<IndexSearchResult> search(float[] queryVector, int k) {
        return index.findNearest(queryVector, k).stream()
                .map(result -> new IndexSearchResult(
                        result.item().id(),
                        result.distance()
                )).collect(Collectors.toList());
    }

    @Override
    public void save(String path) throws IOException {
        index.save(Path.of(path));
        System.out.println("not implemented");
    }

    @Override
    public void load(String path) throws IOException {
        System.out.println("not implemented");
    }
}
