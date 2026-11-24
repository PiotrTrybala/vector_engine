package pl.piotrtrybala.database.executor.async.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.Parser;
import pl.piotrtrybala.database.command.enums.SelectType;
import pl.piotrtrybala.database.command.expression.Expression;
import pl.piotrtrybala.database.command.types.SelectCommand;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingIndex;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingManager;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.ExecutorUtils;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.SelectResult;
import pl.piotrtrybala.database.security.middleware.AuthMiddleware;
import pl.piotrtrybala.database.security.middleware.Middleware;
import pl.piotrtrybala.database.security.middleware.PermissionMiddleware;
import pl.piotrtrybala.database.security.permissions.Permission;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SelectCommandHandler implements CommandHandler{

    private static final Logger LOGGER = LogManager.getLogger(SelectCommandHandler.class.getName());

    @Override
    public AsyncResult handle(Context context, Command command) {
        SelectCommand selectCommand = (SelectCommand) command;

        Expression where = selectCommand.getWhere();

        int limit = selectCommand.getLimit();
        int offset = selectCommand.getOffset();

        Collection currentCollection = ExecutorUtils.selectCollection(context, selectCommand.getCollectionName());

        if (currentCollection == null) {
            return ("Could not find this collection: " + selectCommand.getCollectionName())::toCharArray;
        }

        if (where == null) {
            LOGGER.info("select without where clause");
            SelectType selectType = selectCommand.getType();

            List<Embedding> embeddings;
            try {
                embeddings = MetadataStorage.getInstance().getEmbeddingRepository().findAllWithPaging(
                        currentCollection.getId(),
                        limit,
                        offset
                );
                LOGGER.info("retrieved list of {} embeddings", embeddings.size());
            } catch (RepositoryException e) {
                LOGGER.error("could not retrieve embeddings: {}", e.getMessage());
                return ("Could not retrieve embeddings")::toCharArray;
            }

            if (selectType == SelectType.METADATA) {
                List<String> keys = selectCommand.getMetadata();
                List<HashMap<String, Object>> metadataList = ExecutorUtils.selectMetadataFromEmbeddings(
                        embeddings,
                        keys,
                        true
                );
                return new SelectResult(metadataList, null);
            } else {
                List<HashMap<String, Object>> embeddingsText = ExecutorUtils.selectEmbeddingsText(embeddings);
                return new SelectResult(embeddingsText, null);
            }
        } else {
            LOGGER.info("select with where clause");
            Object vectorValue = where.getValue("vector");
            if (vectorValue != null) {
                float[] vector = (float[]) vectorValue;

                List<EmbeddingIndex.IndexSearchResult> results = EmbeddingManager.getInstance().getIndex().search(vector, limit);

                List<Embedding> embeddings = new ArrayList<>();

                for (EmbeddingIndex.IndexSearchResult result : results) {
                    try {
                        Optional<Embedding> embedding = MetadataStorage.getInstance().getEmbeddingRepository().findById(result.id());
                        embedding.ifPresent(embeddings::add);
                    } catch (RepositoryException e) {
                        return ("Index is not synchronized with database")::toCharArray;
                    }
                }

                return new SelectResult(null, embeddings);

            } else {
                return "Operation is not supported yet"::toCharArray;
            }
        }
    }

    @Override
    public Middleware<Context> getMiddleware() {
        return new PermissionMiddleware(List.of(Permission.COLLECTION_SELECT));
    }
}
