package pl.piotrtrybala.database.executor.async.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.enums.UpdateEntityType;
import pl.piotrtrybala.database.command.expression.Expression;
import pl.piotrtrybala.database.command.types.UpdateCommand;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.ExecutorUtils;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.UpdateResult;
import pl.piotrtrybala.database.security.middleware.AuthMiddleware;
import pl.piotrtrybala.database.security.middleware.Middleware;
import pl.piotrtrybala.database.security.middleware.PermissionMiddleware;
import pl.piotrtrybala.database.security.permissions.Permission;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpdateCommandHandler implements CommandHandler {

    private static final Logger LOGGER = LogManager.getLogger(UpdateCommandHandler.class.getName());

    @Override
    public AsyncResult handle(Context context, Command command) {

        UpdateCommand updateCommand = (UpdateCommand) command;

        Collection currentCollection = ExecutorUtils.selectCollection(context, updateCommand.getCollectionName());

        if (currentCollection == null) {
            return ("Could not find this collection: " + updateCommand.getCollectionName())::toCharArray;
        }

        UpdateEntityType updateEntity = updateCommand.getType();

        if (updateEntity == UpdateEntityType.METADATA) {

            Expression where = updateCommand.getWhere();
            if (where == null) {
                List<Embedding> embeddings;
                try {
                    embeddings = MetadataStorage.getInstance().getEmbeddingRepository().findAll(
                            currentCollection.getId()
                    );
                } catch (RepositoryException e) {
                    LOGGER.error("Could not update embeddings: {}", e.getMessage());
                    return "Could not update embeddings"::toCharArray;
                }

                try {
                    for (Embedding embedding : embeddings) {
                        embedding.setMetadata(updateCommand.getMetadata());
                        MetadataStorage.getInstance().getEmbeddingRepository().update(embedding);
                    }
                } catch (RepositoryException e) {
                    LOGGER.error("Could not update embeddings metadata: {}", e.getMessage());
                    return "Could not update embeddings metadata"::toCharArray;
                }

            } else {
                Object idValue = where.getValue("id");
                Object embeddingValue = where.getValue("vector");

                if (idValue != null) {
                    int id = (int) idValue;

                    Embedding embedding = null;
                    try {
                        Optional<Embedding> e = MetadataStorage.getInstance().getEmbeddingRepository().findById(id);
                        if (e.isPresent()) {
                            embedding = e.get();
                        }
                    } catch (RepositoryException e) {
                        return ("Could not found embedding with id: " + id)::toCharArray;
                    }

                    if (embedding == null) {
                        return ("Could not found embedding with id: " + id)::toCharArray;
                    }

                    try {
                        embedding.setMetadata(updateCommand.getMetadata());
                        MetadataStorage.getInstance().getEmbeddingRepository().update(embedding);
                    } catch (RepositoryException e) {
                        return ("Could not update embedding with id: " + id)::toCharArray;
                    }
                } else if (embeddingValue != null) {
                    float[] embedding = (float[]) embeddingValue;
                    return "Not implemented"::toCharArray;
                }
            }
        }

        return new UpdateResult();
    }

    @Override
    public Middleware<Context> getMiddleware() {
        return new PermissionMiddleware(List.of(Permission.COLLECTION_UPDATE));
    }
}
