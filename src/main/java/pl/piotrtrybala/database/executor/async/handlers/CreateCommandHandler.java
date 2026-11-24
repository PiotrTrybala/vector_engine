package pl.piotrtrybala.database.executor.async.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.types.CreateCommand;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.CreateResult;
import pl.piotrtrybala.database.security.middleware.AuthMiddleware;
import pl.piotrtrybala.database.security.middleware.Middleware;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;

public class CreateCommandHandler implements CommandHandler{

    private static final Logger LOGGER = LogManager.getLogger(CreateCommandHandler.class.getName());

    @Override
    public AsyncResult handle(Context context, Command command) {
        CreateCommand createCommand = (CreateCommand) command;
        LOGGER.info("creating new collection: {}", createCommand.getName());
        try {
            MetadataStorage.getInstance().getCollectionRepository().insert(new Collection(
               context.getSession().getUser().getId(),
               createCommand.getName()
            ));
        } catch(RepositoryException e) {
            LOGGER.error("Could not create collection: {}", e.getMessage());
            return ("Could not create collection: " + e.getMessage())::toCharArray;
        }

        return new CreateResult(createCommand.getName());
    }

    @Override
    public Middleware<Context> getMiddleware() {
        return new AuthMiddleware();
    }
}
