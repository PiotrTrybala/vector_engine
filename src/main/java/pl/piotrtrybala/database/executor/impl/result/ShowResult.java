package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthManager;
import pl.piotrtrybala.database.auth.entities.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.entities.MetadataStorage;
import pl.piotrtrybala.database.entities.RepositoryException;
import pl.piotrtrybala.database.entities.types.Collection;
import pl.piotrtrybala.database.entities.types.Embedding;
import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.enums.UseTargetType;
import pl.piotrtrybala.database.query.types.ShowQuery;

import java.sql.SQLException;
import java.util.List;

public class ShowResult implements ExecutorResult {
    private final SessionStorage sessionStorage;
    private final ShowQuery query;

    public ShowResult(ShowQuery query) {
        this.sessionStorage = AuthManager.getInstance().getStorage();
        this.query = query;
    }

    @Override
    public char[] getResult() {

        UseTargetType useTargetType = query.getEntityType();
        System.out.println(useTargetType.getTarget());

        Session session = this.sessionStorage.retrieve(null);
        if (session == null) {
            return "User is not authenticated".toCharArray();
        }
        User user = session.getUser();
        Collection currentCollection = session.getCurrentCollection();

        if (useTargetType == UseTargetType.COLLECTION) {
            if (currentCollection == null) {
                return "You must select collection".toCharArray();
            }

            return currentCollection.getName().toCharArray();
        }

        if (useTargetType == UseTargetType.USER) {
            String result = "User{id=" + user.getId() + ", username=" + user.getUsername() + "}";
            return result.toCharArray();
        }

        if (useTargetType == UseTargetType.EMBEDDINGS) {

            if (currentCollection == null) {
                return "You must select collection to show all embeddings".toCharArray();
            }
            List<Embedding> embeddingList;
            try {
                embeddingList = MetadataStorage.getInstance().getEmbeddingRepository().list(currentCollection.getId());
            } catch (RepositoryException e) {
                return "Failed to list embeddings".toCharArray();
            }

            String message = "Found " + embeddingList.size() + " embeddings";
            return message.toCharArray();
        }

        List<Collection> collectionList;
        try {
            collectionList = MetadataStorage.getInstance().getCollectionRepository().list(user.getId());
        } catch (SQLException e) {
            String message = "Failed to list collections: " + e.getMessage();
            return message.toCharArray();
        }
        StringBuilder builder = new StringBuilder();

        for (Collection collection : collectionList) {
            builder.append(collection.getName()).append("\n");
        }

        return builder.toString().toCharArray();
    }
}
