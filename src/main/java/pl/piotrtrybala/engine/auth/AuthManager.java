package pl.piotrtrybala.engine.auth;

import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.auth.entities.User;

public class AuthManager {

    public User createUser(String username, String password) {
        return new User();
    }

    public Group createGroup(String name) {
        return new Group();
    }

}
