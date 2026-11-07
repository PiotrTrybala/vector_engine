package pl.piotrtrybala.database;

public class EntrypointConfig {

    public static final String DEFAULT_ROOT_PASSWORD = "root";
    public static final String DEFAULT_USERNAME = "admin";
    public static final String DEFAULT_PASSWORD = "admin";

    // auth
    public static class AuthConfig {
        public final String rootPassword;
        public final String username;
        public final String password;

        public AuthConfig() {
            this.rootPassword = DEFAULT_ROOT_PASSWORD;
            this.username = DEFAULT_USERNAME;
            this.password = DEFAULT_PASSWORD;
        }

        public AuthConfig(String username, String password, String rootPassword) {
            this.username = username;
            this.password = password;
            this.rootPassword = rootPassword;
        }
    }

    public static final int DEFAULT_PORT = 15575;
    public static final int DEFAULT_IDLE_TIMEOUT = 10000;
    public static final int DEFAULT_MAX_QUERY_BUFFER_SIZE = Short.MAX_VALUE;
    public static final int DEFAULT_PROCESSING_WORKER_POOL_SIZE = 10;

    // engine parameters
    public static class EngineConfig {
        public final int port;
        public final int idleTimeout;
        public final int maxQueryBufferSize;
        public final int processingWorkerPoolSize;

        public EngineConfig() {
            this.port = DEFAULT_PORT;
            this.idleTimeout = DEFAULT_IDLE_TIMEOUT;
            this.maxQueryBufferSize = DEFAULT_MAX_QUERY_BUFFER_SIZE;
            this.processingWorkerPoolSize = DEFAULT_PROCESSING_WORKER_POOL_SIZE;
        }

        public EngineConfig(int port, int idleTimeout, int maxQueryBufferSize, int processingWorkerPoolSize) {
            this.port = port;
            this.idleTimeout = idleTimeout;
            this.maxQueryBufferSize = maxQueryBufferSize;
            this.processingWorkerPoolSize = processingWorkerPoolSize;
        }
    }

    public static final int DEFAULT_EMBEDDING_SIZE = 768;
    public static final int DEFAULT_MAX_WRITE_BUFFER_SIZE = Short.MAX_VALUE;

    // buffer
    public static class EmbedderConfig {
        public final int embeddingSize;
        public final int maxWriteBufferSize;

        public EmbedderConfig() {
            this.embeddingSize = DEFAULT_EMBEDDING_SIZE;
            this.maxWriteBufferSize = DEFAULT_MAX_WRITE_BUFFER_SIZE;
        }

        public EmbedderConfig(int embeddingSize, int maxWriteBufferSize) {
            this.embeddingSize = embeddingSize;
            this.maxWriteBufferSize = maxWriteBufferSize;
        }
    }

    private AuthConfig authConfig;
    private EngineConfig engineConfig;
    private EmbedderConfig embedderConfig;

    public EntrypointConfig() {
        this.authConfig = new AuthConfig();
        this.engineConfig = new EngineConfig();
        this.embedderConfig = new EmbedderConfig();
    }

    public AuthConfig getAuthConfig() {
        return authConfig;
    }

    public void setAuthConfig(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    public EngineConfig getEngineConfig() {
        return engineConfig;
    }

    public void setEngineConfig(EngineConfig engineConfig) {
        this.engineConfig = engineConfig;
    }

    public EmbedderConfig getEmbedderConfig() {
        return embedderConfig;
    }

    public void setEmbedderConfig(EmbedderConfig embedderConfig) {
        this.embedderConfig = embedderConfig;
    }
}
