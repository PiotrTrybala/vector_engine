package pl.piotrtrybala.database.executor.blocking.result;

import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.command.types.UseCommand;

import java.util.Optional;

public class UseResult implements Result {

    private final SessionStorage sessionStorage;
    private final UseCommand query;

    public UseResult(UseCommand query) {
        this.sessionStorage = AuthenticationManager.getInstance().getStorage();
        this.query = query;
    }

    @Override
    public char[] getResult() {

        if (!this.sessionStorage.has(null)) {
            return "User is not authenticated".toCharArray();

        }

        // add authorization check

        Session session = this.sessionStorage.retrieve(null);

        User user = session.getUser();
        String collectionName = query.getCollectionName();

        Collection collection = null;

        try {
            Optional<Collection> c = MetadataStorage.getInstance().getCollectionRepository().findById(
                    user.getId(),
                    collectionName
            );

            if (c.isPresent()) {
                collection = c.get();
            }
        } catch(RepositoryException e) {
            return "This collection could not be found".toCharArray();
        }

        session.setCurrentCollection(collection);
        String message = "OK";
        return message.toCharArray();
    }
}
