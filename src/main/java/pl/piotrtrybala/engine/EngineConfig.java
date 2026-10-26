package pl.piotrtrybala.engine;

public class EngineConfig {

    public static class AuthEngineConfig {
        public String rootPassword;
        public String username;
        public String password;
    }

    private int port;
    private int queryBufferMaxSize;
    private int idleTimeout;
    private int maxEmbeddingSize;

    private AuthEngineConfig auth;
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

    public AuthEngineConfig getAuth() {
        return auth;
    }

    public EngineConfig() {}

    public static EngineConfig defaultConfig() {
        EngineConfig config = new EngineConfig();

        config.port = 15575;
        config.queryBufferMaxSize = 16384; // 16kb
        config.maxEmbeddingSize = 768;

        config.collection = "admin";
        config.auth = new AuthEngineConfig();
        config.auth.username = "admin";
        config.auth.password = "admin";
        config.auth.rootPassword = "root";
        return config;
    }

    public static EngineConfig fromConfig(String configPath) {
        return EngineConfig.defaultConfig();
    }
}
