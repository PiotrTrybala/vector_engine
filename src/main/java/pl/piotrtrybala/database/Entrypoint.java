package pl.piotrtrybala.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingManager;
import pl.piotrtrybala.database.engine.Engine;
import pl.piotrtrybala.database.engine.EngineException;
import pl.piotrtrybala.database.engine.impl.EngineImpl;
import pl.piotrtrybala.database.storage.ConnectionManager;
import pl.piotrtrybala.database.storage.MetadataStorage;

public class Entrypoint {

    private static final Logger LOGGER = LogManager.getLogger(Entrypoint.class.getName());

    private EntrypointConfig config;

    private Engine engine;

    private static Entrypoint instance;

    private static volatile boolean initialized = false;

    private Entrypoint() {
    }

    public static synchronized void init() throws RuntimeException {
        Entrypoint.init(null);
        Entrypoint.initialized = true;

        EntrypointConfig config = Entrypoint.getInstance().getConfig();

        AuthenticationManager.initialize(config.getAuthConfig());
        EmbeddingManager.initialize(config.getEmbedderConfig());
        MetadataStorage.initialize();
    }

    public static synchronized void init(EntrypointConfig config) {
        if (config == null) {
            config = new EntrypointConfig();
        }

        if (instance == null) {
            instance = new Entrypoint();
            instance.config = config;
            instance.engine = new EngineImpl(config.getEngineConfig());
        }
    }

    public static synchronized Entrypoint getInstance() {
        if (instance == null) {
            throw new RuntimeException("failed to initialize application");
        }
        return instance;
    }

    public EntrypointConfig getConfig() {
        return config;
    }

    public void run() {
        if (!initialized) {
            throw new RuntimeException("Entrypoint is not initialized");
        }
        LOGGER.info("running engine...");
        try {
            engine.run();
        } catch (EngineException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static synchronized void shutdown() throws RuntimeException {
        ConnectionManager.shutdown();
    }

}
