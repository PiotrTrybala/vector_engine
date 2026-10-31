package pl.piotrtrybala.engine.auth;

import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.auth.entities.User;

import java.sql.Connection;

public class AuthManager {

    private final Connection connection;

    public AuthManager(Connection connection) {
        this.connection = connection;
    }

    public Group createGroup(String name) {
        return new Group();
    }

    public User createUser(String username, String password) {
        return new User();
    }

    public void flush() {

    }

//    public User createUser(String username, String password) {
//        return new User();
//    }
//
//    public Group createGroup(String name) {
//        return new Group();
//    }

}
