package pl.piotrtrybala.engine.executor.impl.result;

import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.auth.Permissions;
import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.auth.session.Session;
import pl.piotrtrybala.engine.auth.session.SessionStorage;
import pl.piotrtrybala.engine.entities.MetadataStorage;
import pl.piotrtrybala.engine.entities.types.Collection;
import pl.piotrtrybala.engine.executor.types.ExecutorResult;
import pl.piotrtrybala.engine.query.enums.EntityType;
import pl.piotrtrybala.engine.query.types.ShowQuery;

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

        EntityType entityType = query.getEntityType();

        Session session = this.sessionStorage.retrieve(null);
        if (session == null) {
            return "User is not authenticated".toCharArray();
        }
        User user = session.getUser();
        Collection currentCollection = session.getCurrentCollection();

        if (entityType == EntityType.COLLECTION) {
            if (currentCollection == null) {
                return "You must select collection".toCharArray();
            }

            return currentCollection.toString().toCharArray();
        }

        if (entityType == EntityType.USER) {
            String result = "User{id=" + user.getId() + ", username=" + user.getUsername() + "}";
            return result.toCharArray();
        }

        List<Collection> collectionList = MetadataStorage.getInstance().getCollectionRepository().list(user.getId());

        StringBuilder builder = new StringBuilder();

        for (Collection collection : collectionList) {
            builder.append(collection.getName()).append("\n");
        }

        return builder.toString().toCharArray();
    }
}
