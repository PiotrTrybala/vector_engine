package pl.piotrtrybala.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.entities.ConnectionManager;

import java.sql.SQLException;

public class Application {

    private static Application instance;

    private Engine engine;
    private EngineConfig engineConfig;

    private static final Logger LOGGER = LogManager.getLogger(Application.class.getName());

    public static synchronized Application getInstance() {
        if (instance == null) {
            instance = new Application();
            instance.engineConfig = new EngineConfig();
            instance.engine = new Engine(instance.engineConfig);
        }
        return instance;
    }

    public static synchronized void initialize(EngineConfig config) {
        LOGGER.info("initializing application...");
        if (instance == null) {
            instance = new Application();
            LOGGER.info("creating application instance...");
            LOGGER.info("created authentication manager...");
            if (config == null) {
                instance.engine = new Engine();
            } else {
                instance.engine = new Engine(config);
            }
            LOGGER.info("created database engine...");

            // add root user
        }
    }

    public void run() {
        LOGGER.info("running application");
        engine.run();
    }

    public EngineConfig getEngineConfig() {
        return engineConfig;
    }
}
