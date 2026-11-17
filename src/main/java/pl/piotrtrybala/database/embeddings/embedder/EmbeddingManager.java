package pl.piotrtrybala.database.embeddings.embedder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.EntrypointConfig;
import pl.piotrtrybala.database.embeddings.embedder.impl.EmbeddingIndexImpl;
import pl.piotrtrybala.database.embeddings.embedder.impl.TestEmbedder;
import pl.piotrtrybala.database.command.enums.EmbeddingType;

import java.util.HashMap;
import java.util.Map;

public class EmbeddingManager {

    private static final Logger LOGGER = LogManager.getLogger(EmbeddingManager.class.getName());

    private EmbeddingIndex index;

    private final Map<EmbeddingType, Embedder> embedders;

    private static EmbeddingManager instance;

    private final EntrypointConfig.EmbedderConfig config;

    private EmbeddingManager(EntrypointConfig.EmbedderConfig config) {
        this.config = config;
        this.embedders = new HashMap<>();
        this.embedders.put(EmbeddingType.TEXT, new TestEmbedder(
                config.embeddingSize()
        ));

        try {
            LOGGER.info("loading index from {}", config.embeddingsIndexPath());
            this.index = EmbeddingIndexImpl.load(config.embeddingsIndexPath(), config.embeddingSize());
        } catch (Exception e) {
            LOGGER.info("no index found. Creating new instance");
            this.index = new EmbeddingIndexImpl(config.maxIndexElementCount(), config.embeddingSize(), 16, 20);
        }
    }

    public static synchronized void initialize(EntrypointConfig.EmbedderConfig config) {
        if (instance != null) {
            throw new IllegalStateException("EmbedderManager is already initialized");
        }
        instance = new EmbeddingManager(config);
    }

    public static synchronized void shutdown() {
        if (instance == null) {
            throw new IllegalStateException("failed to shutdown gracefully");
        }

        try {
            instance.index.save(instance.config.embeddingsIndexPath());
        } catch (Exception e) {
            throw new IllegalStateException("could not save index", e);
        }
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
