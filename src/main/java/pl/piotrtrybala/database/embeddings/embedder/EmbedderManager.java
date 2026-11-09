package pl.piotrtrybala.database.embeddings.embedder;

import pl.piotrtrybala.database.Entrypoint;
import pl.piotrtrybala.database.EntrypointConfig;
import pl.piotrtrybala.database.embeddings.embedder.impl.TestEmbedder;
import pl.piotrtrybala.database.query.enums.EmbeddingType;

import java.util.HashMap;
import java.util.Map;

public class EmbedderManager {

    private final Map<EmbeddingType, Embedder> embedders;

    private static EmbedderManager instance;

    private EmbedderManager(EntrypointConfig.EmbedderConfig config) {
        this.embedders = new HashMap<>();
        this.embedders.put(EmbeddingType.TEXT, new TestEmbedder(
                config.embeddingSize,
                ));
    }

    public static synchronized void initialize(EntrypointConfig.EmbedderConfig config) {
        if (instance != null) {
            throw new IllegalStateException("EmbedderManager is already initialized");
        }
        instance = new EmbedderManager(config);
    }

    public static synchronized EmbedderManager getInstance() {
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

}
