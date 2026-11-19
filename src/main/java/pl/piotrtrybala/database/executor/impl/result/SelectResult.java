package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.command.enums.SelectType;
import pl.piotrtrybala.database.command.expression.Expression;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.command.types.SelectCommand;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.User;

import java.util.List;
import java.util.Optional;

public class SelectResult implements Result {

    private final SelectCommand command;

    public SelectResult(SelectCommand command) {
        this.command = command;
    }

    @Override
    public char[] getResult() {

        Session session = AuthenticationManager.getInstance().getStorage().retrieve(null);
        if (session == null) {
            return "User is not authenticated".toCharArray();
        }
        User user = session.getUser();
        Collection collection = session.getCurrentCollection();

        String collectionName = command.getCollectionName();
        SelectType type = command.getType();
        List<String> metadata = command.getMetadata();
        Expression where = command.getWhere();
        int limit = command.getLimit();

        if (collection == null) {
            try {
                Optional<Collection> c = MetadataStorage.getInstance().getCollectionRepository().findById(
                        user.getId(),
                        collectionName
                );
                if (c.isPresent()) {
                    collection = c.get();
                }
            } catch (RepositoryException e) {
                String message = "Collection could not be found: " + collectionName;
                return message.toCharArray();
            }
        }

        if (collection == null) {
            return "No collection was selected".toCharArray();
        }

        if (type == SelectType.METADATA) {



            return "OK".toCharArray();
        }

        // TEXT SelectType
        return "OK".toCharArray();
    }
}
