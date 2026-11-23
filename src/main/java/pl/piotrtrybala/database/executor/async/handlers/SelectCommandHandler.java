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
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.SelectResult;
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

        if (!context.isAuthenticated()) {
            return "User is not authenticated"::toCharArray;
        }

        Expression where = selectCommand.getWhere();

        int limit = selectCommand.getLimit();
        int offset = selectCommand.getOffset();

        Collection currentCollection = context.getCurrentCollection();
        if (currentCollection == null) {
            try {
                Optional<Collection> collection = MetadataStorage.getInstance().getCollectionRepository().findById(
                        context.getSession().getUser().getId(),
                        selectCommand.getCollectionName()
                );
                if (collection.isPresent()) {
                    currentCollection = collection.get();
                }
            } catch (RepositoryException e) {
                return ("Could not find this collection: " + selectCommand.getCollectionName())::toCharArray;
            }
        }

        if (currentCollection == null) {
            return ("Could not find this collection: " + selectCommand.getCollectionName())::toCharArray;
        }

        if (where == null) {

            SelectType selectType = selectCommand.getType();

            List<Embedding> embeddings;
            try {
                embeddings = MetadataStorage.getInstance().getEmbeddingRepository().findAllWithPaging(
                        currentCollection.getId(),
                        limit,
                        offset
                );
            } catch (RepositoryException e) {
                LOGGER.error("could not retrieve embeddings: {}", e.getMessage());
                return ("Could not retrieve embeddings")::toCharArray;
            }

            if (selectType == SelectType.METADATA) {
                List<String> keys = selectCommand.getMetadata();
                List<HashMap<String, Object>> metadataList = new ArrayList<>();
                for (Embedding embedding : embeddings) {
                    HashMap<String, Object> metadata = new HashMap<>();
                    metadata.put("id", embedding.getId());
                    HashMap<String, Object> embeddingMetadata = embedding.getMetadata();
                    for (String key : keys) {
                        metadata.put(key, embeddingMetadata.get(key));
                    }
                    metadataList.add(metadata);
                }
                return new SelectResult(metadataList, null);
            } else {
                List<HashMap<String, Object>> embeddingsText = new ArrayList<>();
                for (Embedding embedding : embeddings) {
                    HashMap<String, Object> embeddingText = new HashMap<>();
                    embeddingText.put("id", embedding.getId());
                    embeddingText.put("text", embedding.getContent());
                    embeddingsText.add(embeddingText);
                }
                return new SelectResult(embeddingsText, null);
            }
        } else {
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
}
