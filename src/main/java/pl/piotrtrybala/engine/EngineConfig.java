package pl.piotrtrybala.engine;

public class EngineConfig {

    private int port;
    private int queryBufferMaxSize;
    private int idleTimeout;

    private String rootPassword;
    private String username;
    private String password;
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

    public EngineConfig() {}

    public static EngineConfig defaultConfig() {
        EngineConfig config = new EngineConfig();
        config.port = 15575;
        config.queryBufferMaxSize = 16384; // 16kb

        config.collection = "admin";
        config.username = "admin";
        config.password = "admin";
        config.rootPassword = "root";
        return config;
    }

    public static EngineConfig fromConfig(String configPath) {
        return EngineConfig.defaultConfig();
    }
}
