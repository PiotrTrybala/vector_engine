package pl.piotrtrybala.engine.auth;

import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.auth.entities.User;

import java.sql.Connection;

public class AuthManager {

    private final Connection connection;

    public AuthManager(Connection connection) {
        this.connection = connection;
    }

    public Group retrieveGroup(String name) {
        return new Group();
    }

    public Group createGroup(String name) {
        return new Group();
    }

    public User retrieveUser(String name) {
        return new User();
    }

    public User createUser(String username, String password) {
        return new User();
    }

}
