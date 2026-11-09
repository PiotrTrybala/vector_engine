package pl.piotrtrybala.database.embeddings.embedder;

import pl.piotrtrybala.database.embeddings.embedder.impl.TestEmbedder;
import pl.piotrtrybala.database.query.enums.EmbeddingType;

import java.util.HashMap;
import java.util.Map;

public class EmbedderManager {

    private final Map<EmbeddingType, Embedder> embedders;

    private static EmbedderManager instance;

    private EmbedderManager() {
        this.embedders = new HashMap<>();
        this.embedders.put(EmbeddingType.TEXT, new TestEmbedder());
    }

    public static synchronized EmbedderManager getInstance() {
        if (instance == null) {
            instance = new EmbedderManager();
        }
        return instance;
    }

    public Embedder getEmbedder(EmbeddingType type) throws EmbedderException {
        Embedder embedder = this.embedders.get(type);
        if (embedder == null) throw new EmbedderException("embedder for this type is not defined");
        return embedder;
    }

}
