package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.Entrypoint;
import pl.piotrtrybala.database.auth.AuthManager;
import pl.piotrtrybala.database.auth.entities.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.entities.MetadataStorage;
import pl.piotrtrybala.database.entities.types.Collection;
import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.types.UseCollectionQuery;

import java.sql.SQLException;

public class UseCollectionResult implements ExecutorResult {

    private final SessionStorage sessionStorage;
    private final UseCollectionQuery query;

    public UseCollectionResult(UseCollectionQuery query) {
        this.sessionStorage = AuthManager.getInstance().getStorage();
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
