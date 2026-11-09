package pl.piotrtrybala.database.embeddings.embedder.impl;

import com.github.jelmerk.hnswlib.core.DistanceFunctions;
import com.github.jelmerk.hnswlib.core.Item;
import com.github.jelmerk.hnswlib.core.hnsw.HnswIndex;
import pl.piotrtrybala.database.Entrypoint;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingIndex;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmbeddingIndexImpl implements EmbeddingIndex {


    static class FloatVectorItem implements Item<Integer, float[]> {
        private final int id;
        private final int dimensions;
        private final float[] vector;

        public FloatVectorItem(int id, int dimensions, float[] vector) {
            this.id = id;
            this.dimensions = dimensions;
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
            return dimensions;
        }
    }

    private final HnswIndex<Integer, float[], FloatVectorItem, Float> index;
    private final int dimensions;

    public EmbeddingIndexImpl(int maxElements, int dimensions, int m, int efConstruction) {
        this.dimensions = dimensions;
        this.index = HnswIndex.newBuilder(dimensions, DistanceFunctions.FLOAT_COSINE_DISTANCE, maxElements)
                .withM(m)
                .withEfConstruction(efConstruction)
                .build();
    }

    @Override
    public void add(int id, float[] vector) {
        FloatVectorItem item = new FloatVectorItem(id, dimensions, vector);
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
