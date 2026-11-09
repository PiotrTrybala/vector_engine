package pl.piotrtrybala.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.embeddings.embedder.EmbedderManager;
import pl.piotrtrybala.database.engine.Engine;
import pl.piotrtrybala.database.engine.EngineException;
import pl.piotrtrybala.database.engine.impl.EngineImpl;

public class Entrypoint {

    private static final Logger LOGGER = LogManager.getLogger(Entrypoint.class.getName());

    private EntrypointConfig config;

    private Engine engine;

    private static Entrypoint instance;

    private static volatile boolean initialized = false;

    private Entrypoint() {
    }

    public static synchronized void init() {
        Entrypoint.init(null);
        Entrypoint.initialized = true;

        EmbedderManager.initialize(this.config.getEmbedderConfig());
    }

    public static synchronized void init(EntrypointConfig config) {
        if (config == null) {
            config = new EntrypointConfig();
        }

        if (instance == null) {
            instance = new Entrypoint();
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

}
