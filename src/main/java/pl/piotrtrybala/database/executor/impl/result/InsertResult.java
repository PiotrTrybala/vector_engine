package pl.piotrtrybala.database.executor.impl.result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.embeddings.embedder.Embedder;
import pl.piotrtrybala.database.embeddings.embedder.EmbedderException;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingManager;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingUtils;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.enums.EmbeddingType;
import pl.piotrtrybala.database.query.types.InsertCommand;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class InsertResult implements Result {

    private static final Logger LOGGER = LogManager.getLogger(InsertResult.class.getName());

    private final InsertCommand query;

    public InsertResult(InsertCommand query) {
        this.query = query;
    }

    @Override
    public char[] getResult() {

        String collectionName = query.getCollectionName();
        EmbeddingType embeddingType = query.getType();
        byte[] data = this.query.getData();

        Embedder embedder = null;
        try {
            embedder = EmbeddingManager.getInstance().getEmbedder(embeddingType);
        } catch (EmbedderException e) {
            String message = "This data type is not supported: " + embeddingType.getType();
            return message.toCharArray();
        }

        if (!AuthenticationManager.getInstance().getStorage().has(null)) {
            return "User is not authenticated".toCharArray();
        }

        Session session = AuthenticationManager.getInstance().getStorage().retrieve(null);

        User user = session.getUser();
        Collection selectedCollection = session.getCurrentCollection();
        if (selectedCollection == null) {
            try {
                Optional<Collection> collection = MetadataStorage.getInstance().getCollectionRepository().findById(user.getId(), collectionName);
                if (collection.isPresent()) {
                    selectedCollection = collection.get();
                }
            } catch (RepositoryException e) {
                String message = "This collection does not exists: " + collectionName;
                return message.toCharArray();
            }
        }

        if (selectedCollection == null) {
            return "Collection was not found".toCharArray();
        }

        int id;

        try {
            float[] vector = embedder.create(data);
            id = MetadataStorage.getInstance().getEmbeddingRepository().insert(
                    new Embedding(selectedCollection.getId(),
                            data,
                            new HashMap<>())
            );

            Optional<Embedding> emb = MetadataStorage.getInstance().getEmbeddingRepository().findById(id);
            emb.ifPresent(embedding -> EmbeddingManager.getInstance().getIndex().add(embedding.getId(), vector));
        } catch (RepositoryException e) {
            LOGGER.error("could not add embedding: {}", e.getMessage());
            return "Failed to insert new embedding".toCharArray();
        }

        String message = "New embedding added, id = " + id;
        return message.toCharArray();
    }
}
