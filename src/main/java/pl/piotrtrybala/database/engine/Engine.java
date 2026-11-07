package pl.piotrtrybala.database.engine;

public interface Engine {
    void init() throws EngineException;
    void run() throws EngineException;
}
