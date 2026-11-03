package pl.piotrtrybala;

import pl.piotrtrybala.engine.Application;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.auth.Permissions;
import pl.piotrtrybala.engine.auth.entities.User;

import java.sql.SQLException;

public class Main {

    static void main() {

        Application.initialize(null);
        Application.getInstance().run();
//        AuthManager manager = AuthManager.getInstance();
//        try {
//            User user = manager.retrieveUser("root");
//            Permissions permissions = user.getPermissions();
//            permissions.add("config.config.config");
//            user.update();
//
//            System.out.println(user);
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
    }
}

