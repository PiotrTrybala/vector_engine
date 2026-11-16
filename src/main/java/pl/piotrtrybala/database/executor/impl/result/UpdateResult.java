package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.enums.UpdateEntityType;
import pl.piotrtrybala.database.query.expression.Expression;
import pl.piotrtrybala.database.query.types.UpdateCommand;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.types.User;

import java.util.HashMap;

public class UpdateResult implements Result {

    private final UpdateCommand command;

    public UpdateResult(UpdateCommand command) {
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
        Expression expression = command.getWhere();



        if (type == UpdateEntityType.METADATA) {



            if (expression == null) {

                MetadataStorage.getInstance().getEmbeddingRepository().list();

                // update all
            } else {
                // match expression
                // can match both id and vector
            }


            return "OK".toCharArray();
        }

        // UpdateEntityType.EMBEDDING

        // only id (modify only content of one embedding)



        return "Not implemented".toCharArray();
    }
}
