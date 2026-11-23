package pl.piotrtrybala.database.executor.sync.results;

import pl.piotrtrybala.database.security.AuthenticationManager;
import pl.piotrtrybala.database.security.permissions.Permissions;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.security.session.Session;
import pl.piotrtrybala.database.security.SessionManager;
import pl.piotrtrybala.database.storage.repositories.CollectionRepository;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.executor.Result;
import pl.piotrtrybala.database.command.types.CreateCommand;

public class CreateResult implements Result {

    private final SessionManager sessionStorage;
    private final CreateCommand query;

    public CreateResult(CreateCommand query) {
        this.sessionStorage = AuthenticationManager.getInstance().getStorage();
        this.query = query;
    }

    @Override
    public char[] getResult() {

        if (this.sessionStorage.has(null)) {
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
                MetadataStorage.getInstance().getCollectionRepository().insert(new Collection(userId, name));
            } catch (RepositoryException e) {
                return "Failed to create collection".toCharArray();
            }
        } else {
            return "Not authorized".toCharArray();
        }

        return "OK".toCharArray();
    }
}
