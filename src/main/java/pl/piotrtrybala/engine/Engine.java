package pl.piotrtrybala.engine;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Engine {

    private static Engine engine;

    private final EngineConfig config;

    private static final Logger logger = LogManager.getLogger();

    public Engine() {
        this.config = EngineConfig.defaultConfig();
    }

    public Engine(String configPath) {
        this.config = EngineConfig.fromConfig(configPath);
    }

    public Engine(EngineConfig config) {
        this.config = config;
    }

    public static synchronized Engine getInstance() {
        if (engine == null) {
            return new Engine();
        }
        return engine;
    }

    public static synchronized Engine getInstance(EngineConfig config) {
        if (engine == null) {
            return new Engine(config);
        }
        return engine;
    }

    public void run() {
        logger.info("Running Vector Engine");
    }
}
