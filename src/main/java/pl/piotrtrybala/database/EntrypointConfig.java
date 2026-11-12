package pl.piotrtrybala.database;

public class EntrypointConfig {

    public static final String DEFAULT_ROOT_PASSWORD = "root";
    public static final String DEFAULT_USERNAME = "admin";
    public static final String DEFAULT_PASSWORD = "admin";

    // auth
        public record AuthConfig(String username, String password, String rootPassword) {
            public AuthConfig() {
                this(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_ROOT_PASSWORD);
            }

    }

    public static final int DEFAULT_PORT = 15575;
    public static final int DEFAULT_IDLE_TIMEOUT = 10000;
    public static final int DEFAULT_MAX_QUERY_BUFFER_SIZE = Short.MAX_VALUE;
    public static final int DEFAULT_PROCESSING_WORKER_POOL_SIZE = 10;

    // engine parameters
        public record EngineConfig(int port, int idleTimeout, int maxQueryBufferSize, int processingWorkerPoolSize) {
            public EngineConfig() {
                this(DEFAULT_PORT, DEFAULT_IDLE_TIMEOUT, DEFAULT_MAX_QUERY_BUFFER_SIZE, DEFAULT_PROCESSING_WORKER_POOL_SIZE);
            }

    }

    public static final int DEFAULT_EMBEDDING_SIZE = 768;
    public static final int DEFAULT_MAX_WRITE_BUFFER_SIZE = Short.MAX_VALUE;

    // buffer
        public record EmbedderConfig(int embeddingSize, int maxWriteBufferSize) {
            public EmbedderConfig() {
                this(DEFAULT_EMBEDDING_SIZE, DEFAULT_MAX_WRITE_BUFFER_SIZE);
            }

    }

    private final AuthConfig authConfig;
    private final EngineConfig engineConfig;
    private final EmbedderConfig embedderConfig;

    public EntrypointConfig() {
        this.authConfig = new AuthConfig();
        this.engineConfig = new EngineConfig();
        this.embedderConfig = new EmbedderConfig();
    }

    public AuthConfig getAuthConfig() {
        return authConfig;
    }

    public EngineConfig getEngineConfig() {
        return engineConfig;
    }

    public EmbedderConfig getEmbedderConfig() {
        return embedderConfig;
    }
}
