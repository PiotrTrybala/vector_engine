package pl.piotrtrybala.database.embeddings.embedder;

import pl.piotrtrybala.database.EntrypointConfig;
import pl.piotrtrybala.database.embeddings.embedder.impl.EmbeddingIndexImpl;
import pl.piotrtrybala.database.embeddings.embedder.impl.TestEmbedder;
import pl.piotrtrybala.database.query.enums.EmbeddingType;

import java.util.HashMap;
import java.util.Map;

public class EmbeddingManager {

    private final EmbeddingIndex index;

    private final Map<EmbeddingType, Embedder> embedders;

    private static EmbeddingManager instance;

    private EmbeddingManager(EntrypointConfig.EmbedderConfig config) {
        this.embedders = new HashMap<>();
        this.embedders.put(EmbeddingType.TEXT, new TestEmbedder(
                config.embeddingSize
        ));
        // adjust that values
        this.index = new EmbeddingIndexImpl(10000, config.embeddingSize, 16, 20);
    }

    public static synchronized void initialize(EntrypointConfig.EmbedderConfig config) {
        if (instance != null) {
            throw new IllegalStateException("EmbedderManager is already initialized");
        }
        instance = new EmbeddingManager(config);
    }

    public static synchronized EmbeddingManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("manager should be initialized");
        }
        return instance;
    }

    public Embedder getEmbedder(EmbeddingType type) throws EmbedderException {
        Embedder embedder = this.embedders.get(type);
        if (embedder == null) throw new EmbedderException("embedder for this type is not defined");
        return embedder;
    }

    public EmbeddingIndex getIndex() {
        return index;
    }
}
