package pl.piotrtrybala.database.auth;

import pl.piotrtrybala.database.auth.entities.Group;
import pl.piotrtrybala.database.auth.entities.User;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.auth.session.impl.SessionStorageImpl;
import pl.piotrtrybala.database.entities.ConnectionManager;
import pl.piotrtrybala.database.entities.GroupRepository;
import pl.piotrtrybala.database.entities.UserGroupRepository;
import pl.piotrtrybala.database.entities.UserRepository;
import pl.piotrtrybala.database.entities.impl.GroupRepositoryImpl;
import pl.piotrtrybala.database.entities.impl.UserGroupRepositoryImpl;
import pl.piotrtrybala.database.entities.impl.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthManager {

    private final UserRepository userRepo;
    private final GroupRepository groupRepo;
    private final UserGroupRepository userGroupRepo;

    private final SessionStorage storage;

    private static AuthManager instance;

    public AuthManager(Connection connection) {
        this.userRepo = new UserRepositoryImpl(connection);
        this.groupRepo = new GroupRepositoryImpl(connection);
        this.userGroupRepo = new UserGroupRepositoryImpl(connection);
        this.storage = new SessionStorageImpl();
    }

    public synchronized SessionStorage getStorage() {
        return storage;
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

    public User authenticateUser(String username, String password) throws Exception {
        System.out.println("retrieving user: " + username);
        User user = this.retrieveUser(username);
        System.out.println("user found: " + user.toString());
        if (!user.getPassword().equals(password)) {
            throw new Exception("failed to authenticate user");
        }

        return user;
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
