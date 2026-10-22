package pl.piotrtrybala;

import pl.piotrtrybala.engine.Engine;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.auth.Group;
import pl.piotrtrybala.engine.auth.User;
import pl.piotrtrybala.engine.query.Parser;
import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.types.HandshakeQuery;

import java.util.List;

public class Main {
    static void main() {
//        Engine engine = Engine.getInstance();

//        AuthManager authManager = engine.getAuthManager();
//
//        Group group = authManager.createGroup("name1");
//        group.addPermission("collection.create");
//        group.addPermission("collection.delete");
//        group.addPermission("embedding.insert");
//        group.addPermission("*.*");
//        group.flush();
//
//        User user = authManager.createUser("piotrek", "password1");
//        user.assignGroup(group);
//        user.flush();

        // engine.run();

        Parser parser = new Parser();

        String source = "HANDSHAKE;" +
                "AUTH username password;" +
                "CREATE COLLECTION test;"+
                "USE COLLECTION test;" +
                "INSERT INTO test TYPE text BEGIN `this is sample text for testing embedding` END;" +
                "UPDATE embedding VALUE BEGIN `this is new value for embedding` END WHERE vector = [0.3, 0.4, 0.5] OR id = 1;" +
                "UPDATE metadata VALUE [key:12 as int,key2:\"value2\" as text, key3:0.0 as double, key4:true as boolean] WHERE vector = [0.3, 0.4, 0.5] OR id = 1;" +
                "SELECT METADATA key, key2 FROM test WHERE vector=[0.3, 0.4, 0.5] LIMIT 3 ORDER BY key;" + // select metadata from similar texts
                "SELECT SIMILAR FROM test WHERE vector=[0.3, 0.4, 0.5];" + // select similar texts
                "SELECT key, key2 FROM test WHERE id = 1 OR id = [2, 3, 4];"; // or only select from known id or list

        List<Query> queries = null;
        try {
            queries = parser.parse(source.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Query q : queries) {
            System.out.println(q);
        }
    }
}

