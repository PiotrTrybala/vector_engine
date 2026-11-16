package pl.piotrtrybala.database.engine.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.EntrypointConfig;
import pl.piotrtrybala.database.engine.Engine;
import pl.piotrtrybala.database.engine.EngineException;

public class AsyncEngineImpl implements Engine {

    private static final Logger LOGGER = LogManager.getLogger(AsyncEngineImpl.class.getName());

    private final EntrypointConfig.EngineConfig config;

    public AsyncEngineImpl(EntrypointConfig.EngineConfig config) {
        this.config = config;
    }

    @Override
    public void init() throws EngineException {

    }

    @Override
    public void run() throws EngineException {

    }
}
