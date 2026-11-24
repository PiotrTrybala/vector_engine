package pl.piotrtrybala.database.executor;

import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ExecutorUtils {

    public static byte[] toBytes(String str) {
        return StandardCharsets.UTF_8.encode(str).array();
    }

    public static Collection selectCollection(Context context, String collectionName) throws ExecutorException {
        if (context.getCurrentCollection() == null) {
            try {
                Optional<Collection> collection = MetadataStorage.getInstance().getCollectionRepository().findById(
                        context.getSession().getUser().getId(),
                        collectionName
                );
                return collection.orElseThrow(() -> new ExecutorException("Could not select collection"));
            } catch (RepositoryException e) {
                throw new ExecutorException("Could not find this collection: " + collectionName);
            }
        }
        return context.getCurrentCollection();
    }
}