package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.types.UseCollectionQuery;

import java.sql.SQLException;

public class UseCollectionResult implements Result {

    private final SessionStorage sessionStorage;
    private final UseCollectionQuery query;

    public UseCollectionResult(UseCollectionQuery query) {
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
            collection = MetadataStorage.getInstance().getCollectionRepository().retrieve(
                    user.getId(),
                    collectionName
            );
        } catch(SQLException e) {
            return "This collection could not be found".toCharArray();
        }

        session.setCurrentCollection(collection);
        String message = "OK";
        return message.toCharArray();
    }
}
