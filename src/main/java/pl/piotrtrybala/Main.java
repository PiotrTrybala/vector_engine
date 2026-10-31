package pl.piotrtrybala;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.entities.ConnectionManager;

import java.sql.*;

public class Main {

    static Logger LOGGER = LogManager.getLogger();

    static void main() {
//        Engine engine = Engine.getInstance();
//        engine.run();

//        String url = "jdbc:sqlite:metadata.db";
//        String schemaPath = "/metadata.sql";
//
//        String schema;
//        try (InputStream inputStream = Main.class.getResourceAsStream(schemaPath)) {
//            assert inputStream != null;
//            schema = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
//        } catch(IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//
//        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate(schema);
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
        } catch(SQLException e) {
            LOGGER.error("sqlite error: {}", e.getMessage());
            return;
        }

        AuthManager manager = new AuthManager(connection);

        Group rootGroup = manager.createGroup("root");
        rootGroup.addPermission("*.*");
        rootGroup.flush();

        Group adminGroup = manager.createGroup("group1");
        adminGroup.addPermission("collection.create");
        adminGroup.addPermission("embedding.insert");
        adminGroup.flush();

        User root = manager.createUser("root", User.generatePassword());
        root.assignGroup(rootGroup);
        root.flush();

        User admin = manager.createUser("admin", "admin123");
        admin.assignGroup(adminGroup);
        admin.flush();

        Group retrievedAdminGroup = manager.retrieveGroup("group1");
        User retrievedUser = manager.retrieveUser("root");

        LOGGER.info("group: {}", retrievedAdminGroup.toString());
        LOGGER.info("user: {}", retrievedUser.toString());


    }
}

