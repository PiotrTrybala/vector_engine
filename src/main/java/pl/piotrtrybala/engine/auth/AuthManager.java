package pl.piotrtrybala.engine.auth;

import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.entities.GroupRepository;
import pl.piotrtrybala.engine.entities.UserRepository;
import pl.piotrtrybala.engine.entities.impl.GroupRepositoryImpl;
import pl.piotrtrybala.engine.entities.impl.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthManager {

    private UserRepository userRepo;
    private GroupRepository groupRepo;

    public AuthManager(Connection connection) {
        this.userRepo = new UserRepositoryImpl(connection);
        this.groupRepo = new GroupRepositoryImpl(connection);
    }

    public Group retrieveGroup(String name) throws SQLException {
        return this.groupRepo.retrieve(name);
    }

    public Group createGroup(String name) {
        Group group = new Group(groupRepo);
        group.setName(name);
        return group;
    }

    public User retrieveUser(String name) throws SQLException {
        return this.userRepo.retrieve(name);
    }

    public User createUser(String username, String password) {
        User user = new User(userRepo);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

}
