package pl.piotrtrybala;

import pl.piotrtrybala.engine.Engine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class Main {
    static void main() {
//        Engine engine = Engine.getInstance();
//        engine.run();

        String url = "jdbc:sqlite:metadata.db";
        String schemaPath = "/metadata.sql";

        String schema;
        try (InputStream inputStream = Main.class.getResourceAsStream(schemaPath)) {
            assert inputStream != null;
            schema = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }


        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(schema);
        } catch(SQLException e) {
            e.printStackTrace();
        }

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



//        Parser parser = new Parser();
//
//        String source = "HANDSHAKE;" +
//                "AUTH username password;" +
//                "CREATE COLLECTION test;"+
//                "USE COLLECTION test;" +
//                "INSERT INTO test TYPE text BEGIN `this is sample text for testing embedding` END;" +
//                "UPDATE embedding VALUE BEGIN `this is new value for embedding` END WHERE vector = [0.3, 0.4, 0.5] OR id = 1;" +
//                "UPDATE metadata VALUE [key:12 as int,key2:\"value2\" as text, key3:0.0 as double, key4:true as boolean] WHERE vector = [0.3, 0.4, 0.5] OR id = 1;" +
//                "SELECT METADATA [key, key2] FROM test WHERE vector=[0.3, 0.4, 0.5] OR id = 1 LIMIT 3;" + // select metadata from similar texts
//                "SELECT TEXT FROM test WHERE vector = [0.3, 0.4, 0.5] OR id = 1 LIMIT 3;"; // select similar text
//
//        List<Query> queries = null;
//        try {
//            queries = parser.parse(source.toCharArray());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        for (Query q : queries) {
//            System.out.println(q);
//        }
    }
}

