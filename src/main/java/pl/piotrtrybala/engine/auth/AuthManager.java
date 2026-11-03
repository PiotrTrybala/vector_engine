package pl.piotrtrybala.engine.auth;

import pl.piotrtrybala.engine.auth.entities.Group;
import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.entities.ConnectionManager;
import pl.piotrtrybala.engine.entities.GroupRepository;
import pl.piotrtrybala.engine.entities.UserGroupRepository;
import pl.piotrtrybala.engine.entities.UserRepository;
import pl.piotrtrybala.engine.entities.impl.GroupRepositoryImpl;
import pl.piotrtrybala.engine.entities.impl.UserGroupRepositoryImpl;
import pl.piotrtrybala.engine.entities.impl.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthManager {

    private UserRepository userRepo;
    private GroupRepository groupRepo;
    private UserGroupRepository userGroupRepo;

    private static AuthManager instance;

    public AuthManager(Connection connection) {
        this.userRepo = new UserRepositoryImpl(connection);
        this.groupRepo = new GroupRepositoryImpl(connection);
        this.userGroupRepo = new UserGroupRepositoryImpl(connection);
    }

    public static synchronized AuthManager getInstance() {
        try {
            if (instance == null) {
                instance = new AuthManager(ConnectionManager.getConnection());
            }
            return instance;
        } catch(SQLException e) {
            throw new RuntimeException("failed to retrieve auth manager: " + e.getMessage());
        }
    }

    public Group retrieveGroup(String name) throws SQLException {
        return this.groupRepo.retrieve(name);
    }

    public Group createGroup(String name) {
        Group group = new Group(groupRepo);
        group.setName(name);
        return group;
    }

    public User retrieveUser(int id) throws SQLException {
        User user = this.userRepo.retrieve(id);
        return this.retrieveUser(user.getUsername());
    }

    public User retrieveUser(String username) throws SQLException {
        User user = this.userRepo.retrieve(username);
        int groupId = this.userGroupRepo.retrieveGroupId(user.getId());
        if (groupId != -1) {
            Group group = this.groupRepo.retrieve(groupId);
            user.setGroup(group);
        }
        user.setRepo(this.userRepo);
        user.setuGroupRepo(this.userGroupRepo);
        return user;
    }

    public User createUser(String username, String password) {
        User user = new User(userRepo, userGroupRepo);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

}
