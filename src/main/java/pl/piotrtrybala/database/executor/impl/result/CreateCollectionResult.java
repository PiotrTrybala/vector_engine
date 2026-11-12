package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.auth.permissions.Permissions;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.storage.repositories.CollectionRepository;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.types.CreateCollectionQuery;

import java.sql.SQLException;

public class CreateCollectionResult implements Result {

    private final SessionStorage sessionStorage;
    private final CreateCollectionQuery query;

    public CreateCollectionResult(CreateCollectionQuery query) {
        this.sessionStorage = AuthenticationManager.getInstance().getStorage();
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
