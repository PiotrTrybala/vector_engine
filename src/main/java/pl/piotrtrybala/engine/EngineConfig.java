package pl.piotrtrybala.engine;

public class EngineConfig {
    public EngineConfig() {}

    public static EngineConfig defaultConfig() {
        return new EngineConfig();
    }

    public static EngineConfig fromConfig(String configPath) {
        return new EngineConfig();
    }
}
