package pl.piotrtrybala.engine.auth;

public class AuthManager {

    public User createUser(String username, String password) {
        return new User();
    }

    public Group createGroup(String name) {
        return new Group();
    }

}
