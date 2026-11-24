package pl.piotrtrybala.database.executor.async.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.types.InsertCommand;
import pl.piotrtrybala.database.embeddings.embedder.Embedder;
import pl.piotrtrybala.database.embeddings.embedder.EmbedderException;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingManager;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.InsertResult;
import pl.piotrtrybala.database.security.middleware.AuthMiddleware;
import pl.piotrtrybala.database.security.middleware.Middleware;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.util.HashMap;
import java.util.Optional;

public class InsertCommandHandler implements CommandHandler{

    private static final Logger LOGGER = LogManager.getLogger(InsertCommandHandler.class.getName());

    @Override
    public AsyncResult handle(Context context, Command command) {

        InsertCommand insertCommand = (InsertCommand) command;

        Embedder embedder = null;
        try {
            embedder = EmbeddingManager.getInstance().getEmbedder(insertCommand.getType());
        } catch(EmbedderException e) {
            return ("This datatype is not supported: " + insertCommand.getType().toString())::toCharArray;
        }

        Collection currentCollection = context.getCurrentCollection();
        if (currentCollection == null) {
            try {
                Optional<Collection> collection = MetadataStorage.getInstance().getCollectionRepository().findById(
                        context.getSession().getUser().getId(),
                        insertCommand.getCollectionName()
                );
                if (collection.isPresent()) {
                    currentCollection = collection.get();
                }
            } catch(RepositoryException e) {
                return ("Could not find this collection: " + insertCommand.getCollectionName())::toCharArray;
            }
        }

        if (currentCollection == null) {
            return ("Could not find this collection: " + insertCommand.getCollectionName())::toCharArray;
        }

        int id = -1;

        try {
            float[] vector = embedder.create(insertCommand.getData());
            id = MetadataStorage.getInstance().getEmbeddingRepository().insert(
                    new Embedding(
                            currentCollection.getId(),
                            insertCommand.getData(),
                            new HashMap<>()
                    )
            );
            EmbeddingManager.getInstance().getIndex().add(id, vector);
        } catch(RepositoryException e) {
            LOGGER.error("Could not insert embedding into collection: {}", e.getMessage());
            return ("Could not insert embedding into collection: " + e.getMessage())::toCharArray;
        }
        return new InsertResult(id);
    }

    @Override
    public Middleware<Context> getMiddleware() {
        return new AuthMiddleware();
    }
}
