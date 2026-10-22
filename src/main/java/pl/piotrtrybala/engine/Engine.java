package pl.piotrtrybala.engine;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.query.Parser;
import pl.piotrtrybala.engine.query.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Engine {

    private static Engine engine;
    private final EngineConfig config;
    private final AuthManager authManager;
    private static final Logger logger = LogManager.getLogger();

    private ServerSocket socket;

    public Engine() {
        this.config = EngineConfig.defaultConfig();
        this.authManager = new AuthManager();
        this.init();
    }

    public Engine(String configPath) {
        this.config = EngineConfig.fromConfig(configPath);
        this.authManager = new AuthManager();
        this.init();
    }

    public Engine(EngineConfig config) {
        this.config = config;
        this.authManager = new AuthManager();
        this.init();
    }

    private void init() {
        try {
            logger.info("initializing server port {}", this.config.getPort());
            this.socket = new ServerSocket(this.config.getPort());
        } catch (IOException e) {
            logger.error("failed to initialize server: {}", e.getMessage());
            throw new RuntimeException(e);
        }
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

    public AuthManager getAuthManager() {
        return authManager;
    }

    public void run() {
        logger.info("accepting connections...");
        while (true) {
            try {
                Socket client = socket.accept();
                logger.info("client connected: {}", client.getRemoteSocketAddress());

                handleClient(client);
            } catch (IOException e) {
                logger.error("error accepting client connection: {}", e.getMessage());
            }
        }
    }

    private void handleClient(Socket client) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            this.socket.setSoTimeout(this.config.getIdleTimeout());

            char[] buffer = new char[this.config.getQueryBufferMaxSize()];

            while (!client.isClosed()) {
                int result = reader.read(buffer, 0, this.config.getQueryBufferMaxSize());
                if (result == -1) {
                    logger.info("client closed the connection");
                    break;
                }

                Parser parser = new Parser();
                List<Query> queries = parser.parse(buffer);
                System.out.println("queries = " + queries);

                // 0. Auth check (is_auth = 1)

                // 1. Parse queries

                // 2. Pass to scheduler

                // 2.5. Executor authorization (can_do_op = 1)

                // 3. Receive response

                // 4. Send response to client

//                logger.info("received data: {}", buffer);
            }
        } catch (IOException e) {
            logger.error("failed to receive data from client: {}", e.getMessage());
        } finally {
            try {
                client.close();
                logger.info("client disconnected");
            } catch (IOException e) {
                logger.error("failed to close client socket: {}", e.getMessage());
            }
        }
    }
}
