package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.enums.UpdateEntityType;
import pl.piotrtrybala.database.query.expression.Expression;
import pl.piotrtrybala.database.query.types.UpdateCommand;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;
import pl.piotrtrybala.database.storage.types.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateResult implements Result {

    private final UpdateCommand command;

    public UpdateResult(UpdateCommand command) {
        this.command = command;
    }


    @Override
    public char[] getResult() {

        Session session = AuthenticationManager.getInstance().getStorage().retrieve(null);
        if (session == null) {
            return "User is not authenticated".toCharArray();
        }
        User user = session.getUser();
        Collection collection = session.getCurrentCollection();

        String collectionName = command.getCollectionName();
        UpdateEntityType type = command.getType();
        HashMap<String, Object> metadata = command.getMetadata();
        byte[] data = command.getData();
        Expression where = command.getWhere();

        if (collection == null) {
            try {
                collection = MetadataStorage.getInstance().getCollectionRepository().retrieve(
                        user.getId(),
                        collectionName
                );
            } catch(SQLException e) {
                String message = "Collection could not be found: " + collectionName;
                return message.toCharArray();
            }
        }

        if (type == UpdateEntityType.METADATA) {

            if (where == null) {
                List<Embedding> embeddingsList = new ArrayList<>();
                try {
                    embeddingsList = MetadataStorage.getInstance().getEmbeddingRepository().list(
                            collection.getId()
                    );
                } catch (RepositoryException e) {
                    return "could not retrieve embeddings".toCharArray();
                }

                // update all

                try {
                    for (Embedding e : embeddingsList) {
                        e.setMetadata(metadata);
                        e.update();
                    }
                } catch(RepositoryException e) {
                    String message = "could not update embedding metadata";
                    return  message.toCharArray();
                }
            } else {
                // match expression
                // can match both id and vector

                Object idValue = where.getValue("id");
                Object embeddingValue = where.getValue("vector");

                if (idValue != null) {
                    int id = (int) idValue;
                    Embedding embedding = null;
                    try {
                        embedding = MetadataStorage.getInstance().getEmbeddingRepository().retrieve(id);
                    } catch(RepositoryException e) {
                        String message = "Could not found embedding with id: " + id;
                        return message.toCharArray();
                    }

                    try {
                        embedding.setMetadata(metadata);
                        embedding.update();
                    } catch(RepositoryException e) {
                        String message = "could not update embedding with id: " + id;
                        return message.toCharArray();
                    }
                } else if (embeddingValue != null) {
                    float[] embedding = (float[]) embeddingValue;
                    return "TODO: Implement update by vector".toCharArray();
                } else {
                    return "Advanced filtering is not supported".toCharArray();
                }
            }

            return "OK".toCharArray();
        }

        // TODO: Implement embedding updating
        // UpdateEntityType.EMBEDDING

        // only id (modify only content of one embedding)



        return "Not implemented".toCharArray();
    }
}
