package pl.piotrtrybala.database.executor.async.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.types.UseCommand;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.UseResult;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;

import java.util.Optional;

public class UseCommandHandler implements CommandHandler {

    private static final Logger LOGGER = LogManager.getLogger(UseCommandHandler.class.getName());

    @Override
    public AsyncResult handle(Context context, Command command) {

        UseCommand useCommand = (UseCommand) command;

        if (context.getSession() == null) {
            return "User is not authenticated"::toCharArray;
        }

        Optional<Collection> collection;
        try {
            collection = MetadataStorage.getInstance().getCollectionRepository().findById(
                    context.getSession().getUser().getId(),
                    useCommand.getCollectionName()
            );
        } catch(RepositoryException e) {
            LOGGER.error("could not find collection: {}", e.getMessage());
            return "Could not find collection"::toCharArray;
        }

        collection.ifPresent(context::setCurrentCollection);

        return new UseResult();
    }
}
