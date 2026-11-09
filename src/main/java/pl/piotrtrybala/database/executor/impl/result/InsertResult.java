package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthManager;
import pl.piotrtrybala.database.auth.entities.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.embeddings.embedder.Embedder;
import pl.piotrtrybala.database.embeddings.embedder.EmbedderException;
import pl.piotrtrybala.database.embeddings.embedder.EmbedderManager;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingUtils;
import pl.piotrtrybala.database.entities.MetadataStorage;
import pl.piotrtrybala.database.entities.MetadataUtils;
import pl.piotrtrybala.database.entities.RepositoryException;
import pl.piotrtrybala.database.entities.types.Collection;
import pl.piotrtrybala.database.entities.types.Embedding;
import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.enums.EmbeddingType;
import pl.piotrtrybala.database.query.types.InsertQuery;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;

public class InsertResult implements ExecutorResult {

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
            embedder = EmbedderManager.getInstance().getEmbedder(embeddingType);
        } catch (EmbedderException e) {
            String message = "This data type is not supported: " + embeddingType.getType();
            return message.toCharArray();
        }

        if (!AuthManager.getInstance().getStorage().has(null)) {
            return "User is not authenticated".toCharArray();
        }

        Session session = AuthManager.getInstance().getStorage().retrieve(null);

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

        } catch (RepositoryException e) {
            return "Failed to insert new embedding".toCharArray();
        }

        String message = "New embedding added, Id = ";
        return message.toCharArray();
    }
}
