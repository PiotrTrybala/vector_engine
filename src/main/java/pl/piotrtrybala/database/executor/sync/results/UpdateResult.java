package pl.piotrtrybala.database.executor.sync.results;

import pl.piotrtrybala.database.security.AuthenticationManager;
import pl.piotrtrybala.database.security.session.Session;
import pl.piotrtrybala.database.executor.Result;
import pl.piotrtrybala.database.command.enums.UpdateEntityType;
import pl.piotrtrybala.database.command.expression.Expression;
import pl.piotrtrybala.database.command.types.UpdateCommand;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;
import pl.piotrtrybala.database.storage.types.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UpdateResult implements Result {

    private final UpdateCommand command;

    public UpdateResult(UpdateCommand command) {
        this.command = command;
    }

    @Override
    public char[] getResult() {

//        Session session = AuthenticationManager.getInstance().getStorage().retrieve(null);
//        if (session == null) {
//            return "User is not authenticated".toCharArray();
//        }
//        User user = session.getUser();
//        Collection collection = session.getCurrentCollection();
//
//        String collectionName = command.getCollectionName();
//        UpdateEntityType type = command.getType();
//        HashMap<String, Object> metadata = command.getMetadata();
//        byte[] data = command.getData();
//        Expression where = command.getWhere();
//
//        if (collection == null) {
//            try {
//                Optional<Collection> c = MetadataStorage.getInstance().getCollectionRepository().findById(
//                        user.getId(),
//                        collectionName
//                );
//                if (c.isPresent()) {
//                    collection = c.get();
//                }
//            } catch (RepositoryException e) {
//                String message = "Collection could not be found: " + collectionName;
//                return message.toCharArray();
//            }
//        }
//
//        if (collection == null) {
//            return "No collection was selected".toCharArray();
//        }
//
//        if (type == UpdateEntityType.METADATA) {
//
//            if (where == null) {
//                List<Embedding> embeddingsList = new ArrayList<>();
//                try {
//                    embeddingsList = MetadataStorage.getInstance().getEmbeddingRepository().findAll(
//                            collection.getId()
//                    );
//                } catch (RepositoryException e) {
//                    return "could not retrieve embeddings".toCharArray();
//                }
//
//                // update all
//
//                try {
//                    for (Embedding e : embeddingsList) {
//                        e.setMetadata(metadata);
//                        MetadataStorage.getInstance().getEmbeddingRepository().update(e);
//                    }
//                } catch (RepositoryException e) {
//                    String message = "could not update embedding metadata";
//                    return message.toCharArray();
//                }
//            } else {
//                // match expression
//                // can match both id and vector
//
//                Object idValue = where.getValue("id");
//                Object embeddingValue = where.getValue("vector");
//
//                if (idValue != null) {
//                    int id = (int) idValue;
//                    Optional<Embedding> embedding;
//                    try {
//                        embedding = MetadataStorage.getInstance().getEmbeddingRepository().findById(id);
//                    } catch (RepositoryException e) {
//                        String message = "Could not found embedding with id: " + id;
//                        return message.toCharArray();
//                    }
//
//                    try {
//                        if (embedding.isPresent()) {
//                            embedding.get().setMetadata(metadata);
//                            MetadataStorage.getInstance().getEmbeddingRepository().update(embedding.get());
//                        }
//                    } catch (RepositoryException e) {
//                        String message = "could not update embedding with id: " + id;
//                        return message.toCharArray();
//                    }
//                } else if (embeddingValue != null) {
//                    float[] embedding = (float[]) embeddingValue;
//                    return "TODO: Implement update by vector".toCharArray();
//                } else {
//                    return "Advanced filtering is not supported".toCharArray();
//                }
//            }
//
//            return "OK".toCharArray();
//        }
//
//        // TODO: Implement embedding updating
//        // UpdateEntityType.EMBEDDING
//
//        // only id (modify only content of one embedding)
//

        return "Not implemented".toCharArray();
    }
}
