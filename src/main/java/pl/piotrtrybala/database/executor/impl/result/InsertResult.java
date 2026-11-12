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
import pl.piotrtrybala.database.query.types.InsertQuery;

import java.sql.SQLException;
import java.util.HashMap;

public class InsertResult implements Result {

    private static final Logger LOGGER = LogManager.getLogger(InsertResult.class.getName());

    private final InsertQuery query;

    public InsertResult(InsertQuery query) {
        this.query = query;
    }

    @Override
    public char[] getResult() {

        String collectionName = query.getCollectionName();
        EmbeddingType embeddingType = query.getType();
        byte[] data = EmbeddingUtils.toBytes(this.query.getData());

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
                selectedCollection = MetadataStorage.getInstance().getCollectionRepository().retrieve(user.getId(), collectionName);
            } catch (SQLException e) {
                String message = "This collection does not exists: " + collectionName;
                return message.toCharArray();
            }
        }

        Embedding embedding = null;
        try {
            float[] vector = embedder.create(data);
            embedding = MetadataStorage.getInstance().getEmbeddingRepository().create(
                selectedCollection.getId(),
                    data,
                    new HashMap<>()
            );
            EmbeddingManager.getInstance().getIndex().add(embedding.getId(), vector);
        } catch (RepositoryException e) {
            LOGGER.error("could not add embedding: {}", e.getMessage());
            return "Failed to insert new embedding".toCharArray();
        }

        String message = "New embedding added, id = " + embedding.getId();
        return message.toCharArray();
    }
}
