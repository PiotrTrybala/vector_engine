package pl.piotrtrybala.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.entities.ConnectionManager;

import java.sql.SQLException;

public class Application {

    private static final Logger LOGGER = LogManager.getLogger(Application.class.getName());

    private ApplicationConfig config;

    private Engine engine;

    public static Application instance;

    private Application() {}

    public static synchronized void init() {
        Application.init(null);
    }

    public static synchronized void init(ApplicationConfig config) {
        if (config == null) {
            config = new ApplicationConfig();
        }

        if (instance == null) {
            instance = new Application();
            instance.engine = new Engine(config.getEngineConfig());
        }
    }

    public static synchronized Application getInstance() {
        if (instance == null) {
            throw new RuntimeException("failed to initialize application");
        }
        return instance;
    }

    public ApplicationConfig getConfig() {
        return config;
    }

    public void run() {
        LOGGER.info("running engine...");
        engine.run();
    }

}
