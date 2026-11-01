package pl.piotrtrybala;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.entities.ConnectionManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class Main {

    static Logger LOGGER = LogManager.getLogger();

    static void main() throws SQLException {
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
        Connection connection = ConnectionManager.getConnection();
//
        AuthManager manager = new AuthManager(connection);

//        Group rootGroup = manager.createGroup("root");
//        rootGroup.addPermission("*.*");
//        rootGroup.flush();

//        Group adminGroup = manager.createGroup("group1");
//        adminGroup.addPermission("collection.create");
//        adminGroup.addPermission("embedding.insert");
//        adminGroup.flush();

        Group retrievedGroup = manager.retrieveGroup("root");
        LOGGER.info("group: {}", retrievedGroup.toString());

//        User root = manager.createUser("root", User.generatePassword());
//        root.assignGroup(retrievedGroup);
//        root.flush();

//        User admin = manager.createUser("admin", "admin123");
//        admin.assignGroup(adminGroup);
//        admin.flush();

        User retrievedUser = manager.retrieveUser("root");
        retrievedUser.assignGroup(retrievedGroup);
        retrievedUser.update();

        LOGGER.info("user: {}", retrievedUser.toString());


    }
}

