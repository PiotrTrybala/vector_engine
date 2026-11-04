package pl.piotrtrybala.engine;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.entities.ConnectionManager;
import pl.piotrtrybala.engine.executor.Executor;
import pl.piotrtrybala.engine.executor.impl.STExecutor;
import pl.piotrtrybala.engine.executor.types.ExecutorPayload;
import pl.piotrtrybala.engine.executor.types.ExecutorResult;
import pl.piotrtrybala.engine.query.Parser;
import pl.piotrtrybala.engine.query.Query;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Engine {

    private final ApplicationConfig.EngineConfig config;
    private static final Logger logger = LogManager.getLogger(Engine.class.getName());

    private Executor executor;
    private ServerSocket socket;

    public Engine() {
        this.config = new ApplicationConfig.EngineConfig();
        this.init();
    }

    public Engine(ApplicationConfig.EngineConfig config) {
        this.config = config;
        this.init();
    }

    private void init() {
        this.executor = new STExecutor();
        try {
            logger.info("initializing engine on port {}", this.config.port);
            this.socket = new ServerSocket(this.config.port);
        } catch (IOException e) {
            logger.error("failed to initialize server: {}", e.getMessage());
            throw new RuntimeException(e);
        }
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
            this.socket.setSoTimeout(this.config.idleTimeout);

            char[] buffer = new char[this.config.maxQueryBufferSize];

            while (!client.isClosed()) {
                Arrays.fill(buffer, (char) 0);
                int size = reader.read(buffer, 0, this.config.maxQueryBufferSize);
                if (size == -1) {
                    logger.info("client closed the connection");
                    break;
                }
                Parser parser = new Parser();
                List<Query> queries;
                try {
                    queries = parser.parse(buffer);
                    System.out.println(queries);
                    for (Query query : queries) {
                        ExecutorResult result = this.executor.process(new ExecutorPayload(query));
                        char[] output = result.getResult();
                        logger.info("executor processed with result: {}", output);
                        writer.write(output);
                        writer.flush();
                    }
                } catch (Exception e) {
                    logger.error("failed to processing query: {}", e.getMessage());
                    writer.write(e.getMessage());
                    writer.flush();
                }
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
