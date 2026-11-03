package pl.piotrtrybala.engine;

import pl.piotrtrybala.engine.embeddings.storage.BufferManager;

public class EngineConfig {

    public static class AuthEngineConfig {
        public String rootPassword;
        public String username;
        public String password;
    }

    public static class BufferManagerConfig {
        public int maxBufferSize = 32687;
        public int embeddingSize = 768;
    }

    private int port;
    private int queryBufferMaxSize;
    private int idleTimeout;
    private int maxEmbeddingSize;

    private AuthEngineConfig authEngineConfig;
    private BufferManagerConfig bufferManagerConfig;
    private String collection;

    public int getPort() {
        return port;
    }

    public int getQueryBufferMaxSize() {
        return queryBufferMaxSize;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public AuthEngineConfig getAuthEngineConfig() {
        return authEngineConfig;
    }

    public BufferManagerConfig getBufferManagerConfig() { return bufferManagerConfig; }


    public EngineConfig() {}

    public static EngineConfig defaultConfig() {
        EngineConfig config = new EngineConfig();

        config.port = 15575;
        config.queryBufferMaxSize = 16384; // 16kb
        config.maxEmbeddingSize = 768;

        config.collection = "admin";
        config.authEngineConfig = new AuthEngineConfig();
        config.authEngineConfig.username = "admin";
        config.authEngineConfig.password = "admin";
        config.authEngineConfig.rootPassword = "root";
        return config;
    }

    public static EngineConfig fromConfig(String configPath) {
        return EngineConfig.defaultConfig();
    }
}
