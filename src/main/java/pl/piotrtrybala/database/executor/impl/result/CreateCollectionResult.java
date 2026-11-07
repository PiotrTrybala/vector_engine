package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthManager;
import pl.piotrtrybala.database.auth.Permissions;
import pl.piotrtrybala.database.auth.entities.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.entities.CollectionRepository;
import pl.piotrtrybala.database.entities.MetadataStorage;
import pl.piotrtrybala.database.entities.types.Collection;
import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.types.CreateCollectionQuery;

import java.sql.SQLException;

public class CreateCollectionResult implements ExecutorResult {

    private final SessionStorage sessionStorage;
    private final CreateCollectionQuery query;

    public CreateCollectionResult(CreateCollectionQuery query) {
        this.sessionStorage = AuthManager.getInstance().getStorage();
        this.query = query;
    }

    @Override
    public char[] getResult() {

        if (!this.sessionStorage.has(null)) {
            return "User is not authentiated".toCharArray();
        }

        Session session = this.sessionStorage.retrieve(null);
        User user = session.getUser();

        Permissions userPermissions = user.getPermissions();
        Permissions groupPermissions = user.getGroup().getPermissions();

        if (userPermissions.has("*.*") || groupPermissions.has("*.*") || userPermissions.has("collection.create") || groupPermissions.has("collection.create")) {
            CollectionRepository repo = MetadataStorage.getInstance().getCollectionRepository();
            int userId = user.getId();
            String name = query.getName();
            try {
                Collection collection = repo.create(userId, name);
            } catch (SQLException e) {
                return "Failed to create collection".toCharArray();
            }
        } else {
            return "Not authorized".toCharArray();
        }

        return "OK".toCharArray();
    }
}
