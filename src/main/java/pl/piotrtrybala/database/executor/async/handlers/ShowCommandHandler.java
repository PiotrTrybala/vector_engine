package pl.piotrtrybala.database.executor.async.handlers;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.enums.UseTarget;
import pl.piotrtrybala.database.command.types.ShowCommand;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.ShowResult;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.util.List;

public class ShowCommandHandler implements CommandHandler {
    @Override
    public AsyncResult handle(Context context, Command command) {

        ShowCommand showCommand = (ShowCommand) command;

        UseTarget useTarget = showCommand.getUseTarget();

        if (context.getSession() == null) {
            return "Not authenticated"::toCharArray;
        }

        Collection collection = context.getCurrentCollection();

        if (useTarget == UseTarget.COLLECTION) {
            if (collection == null) {
                return "Collection not selected"::toCharArray;
            }
            return new ShowResult(collection, null, null, null);
        }

        if (useTarget == UseTarget.USER) {
            return new ShowResult(null, context.getSession().getUser(), null, null);
        }

        if (useTarget == UseTarget.COLLECTIONS) {
            List<Collection> collectionList;
            try {
                collectionList = MetadataStorage.getInstance().getCollectionRepository().findAll(context.getSession().getUser().getId());
            } catch(RepositoryException e) {
                return "Could not retrieve collections"::toCharArray;
            }
            return new ShowResult(null, null, collectionList, null);
        }
        if (collection == null) {
            return "Current collection is not selected"::toCharArray;
        }
        List<Embedding> embeddings = null;

        try {
            embeddings = MetadataStorage.getInstance().getEmbeddingRepository().findAll(collection.getId());
        } catch(RepositoryException e) {
            return "Could not list embeddings from current collection"::toCharArray;
        }

        return new ShowResult(null, null, null, embeddings);
    }
}
