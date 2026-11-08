package pl.piotrtrybala.database.engine.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.EntrypointConfig;
import pl.piotrtrybala.database.engine.Engine;
import pl.piotrtrybala.database.engine.EngineException;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.impl.STExecutor;
import pl.piotrtrybala.database.executor.types.ExecutorPayload;
import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.Parser;
import pl.piotrtrybala.database.query.Query;
import pl.piotrtrybala.database.query.QueryException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class EngineImpl implements Engine {

    private final EntrypointConfig.EngineConfig config;
    private static final Logger LOGGER = LogManager.getLogger(EngineImpl.class.getName());

    private Executor executor;
    private Parser parser;
    private ServerSocket serverSocket;

    public EngineImpl() {
        this.config = new EntrypointConfig.EngineConfig();
        try {
            init();
        } catch(EngineException e) {
            LOGGER.error("error during engine init: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public EngineImpl(EntrypointConfig.EngineConfig config) {
        this.config = config;
        this.parser = new Parser();
        try {
            init();
        } catch(EngineException e) {
            LOGGER.error("error during engine init: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void init() throws EngineException {
        this.executor = new STExecutor();
        try {
            LOGGER.info("init database on port {}", this.config.port);
            this.serverSocket = new ServerSocket(this.config.port);
//            this.serverSocket.setSoTimeout(this.config.idleTimeout);
        } catch(IOException e) {
            LOGGER.error("error during database init: {}", e.getMessage());
            throw new EngineException(e.getMessage());
        }
    }

    @Override
    public void run() throws EngineException {
        LOGGER.info("accepting connections...");
        while(true) {
            try {
                Socket client = serverSocket.accept();
                LOGGER.info("accepted new client: {}", client.getRemoteSocketAddress());
                this.handleClient(client);
            } catch(IOException e) {
                LOGGER.error("error during accepting new client: {}", e.getMessage());
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            char[] buffer = new char[this.config.maxQueryBufferSize];

            while(!clientSocket.isClosed()) {
                Arrays.fill(buffer, (char) 0);
                int size = reader.read(buffer, 0, this.config.maxQueryBufferSize);
                if (size == -1) {
                    LOGGER.info("client has closed connection");
                    clientSocket.close();
                    break;
                }

                LOGGER.info("received buffer: {}", buffer);

                List<Query> queries;
                try {
                    queries = parser.parse(buffer);
                    for (Query query : queries) {
                        System.out.println(query.toString());
                    }
                    LOGGER.info("parsed {} queries", queries.size());
                    for (Query query : queries) {
                        LOGGER.info("starting processjing {}", query.getQueryType().toString());
                        ExecutorResult result = executor.process(new ExecutorPayload(query));
                        char[] output = result.getResult();
                        LOGGER.info("executor processed with result: {}", output);
                        writer.write(output);
                        writer.flush();
                    }
                } catch(QueryException e) {
                    LOGGER.error("query - failed to parse query: {}", e.getMessage());
                    writer.write(e.getMessage());
                    writer.flush();
                } catch (Exception e) {
                    LOGGER.error("failed to parse query: {}", e.getMessage());
                    writer.write(e.getMessage());
                    writer.flush();
                }
            }



        } catch(IOException e) {
            LOGGER.error("failed to receive data from client: {}", e.getMessage());
        } finally {
            try {
                clientSocket.close();
                LOGGER.info("client disconnected");
            } catch(IOException e) {
                LOGGER.error("failed to close client socket: {}", e.getMessage());
            }
        }
    }
}

