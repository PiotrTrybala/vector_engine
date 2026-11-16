package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.enums.UpdateEntityType;
import pl.piotrtrybala.database.query.expression.Expression;
import pl.piotrtrybala.database.query.types.UpdateQuery;
import pl.piotrtrybala.database.storage.types.User;

import java.util.HashMap;

public class UpdateResult implements Result {

    private final UpdateQuery command;

    public UpdateResult(UpdateQuery command) {
        this.command = command;
    }


    @Override
    public char[] getResult() {
        Session session = AuthenticationManager.getInstance().getStorage().retrieve(null);
        if (session == null) {
            return "User is not authenticated".toCharArray();
        }
        User user = session.getUser();

        UpdateEntityType type = command.getType();
        HashMap<String, Object> metadata = command.getMetadata();
        byte[] data = command.getData();
        Expression expression = command.getWhereClause();



        if (type == UpdateEntityType.METADATA) {
            return "OK".toCharArray();
        }

        // UpdateEntityType.EMBEDDING

        return "OK".toCharArray();
    }
}
