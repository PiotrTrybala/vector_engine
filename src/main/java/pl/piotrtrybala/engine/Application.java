package pl.piotrtrybala.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.entities.ConnectionManager;

import java.sql.SQLException;

public class Application {

    private static Application instance;

    private Engine engine;
    private static AuthManager authManager;

    private static final Logger LOGGER = LogManager.getLogger(Application.class.getName());

    public static synchronized Application getInstance() {
        try {
            if (instance == null) {
                instance = new Application();
                authManager = new AuthManager(ConnectionManager.getConnection());
                instance.engine = new Engine();
            }
            return instance;
        } catch (SQLException e) {
            throw new RuntimeException("failed to initialize application instance: " + e.getMessage());
        }
    }

    public static synchronized void initialize(EngineConfig config) {
        try {
            LOGGER.info("initializing application...");
            if (instance == null) {
                instance = new Application();
                LOGGER.info("creating application instance...");
                authManager = new AuthManager(ConnectionManager.getConnection());
                LOGGER.info("created authentication manager...");
                if (config == null) {
                    instance.engine = new Engine();
                } else {
                    instance.engine = new Engine(config);
                }
                LOGGER.info("created database engine...");
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to initialize application instance: " + e.getMessage());
        }
    }

    public static synchronized AuthManager getAuthManager() {
        return authManager;
    }

    public void run() {
        LOGGER.info("running application");
        engine.run();
    }

}
