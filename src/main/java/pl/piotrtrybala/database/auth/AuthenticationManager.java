package pl.piotrtrybala.database.auth;

import pl.piotrtrybala.database.EntrypointConfig;
import pl.piotrtrybala.database.storage.MetadataUtils;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Group;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.auth.session.SessionStorage;
import pl.piotrtrybala.database.auth.session.impl.SessionStorageImpl;
import pl.piotrtrybala.database.storage.ConnectionManager;
import pl.piotrtrybala.database.storage.repositories.GroupRepository;
import pl.piotrtrybala.database.storage.repositories.UserGroupRepository;
import pl.piotrtrybala.database.storage.repositories.UserRepository;
import pl.piotrtrybala.database.storage.impl.GroupRepositoryImpl;
import pl.piotrtrybala.database.storage.impl.UserGroupRepositoryImpl;
import pl.piotrtrybala.database.storage.impl.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class AuthenticationManager {

    private final UserRepository userRepo;
    private final GroupRepository groupRepo;
    private final UserGroupRepository userGroupRepo;

    private final SessionStorage storage;

    private static AuthenticationManager instance;

    private AuthenticationManager(Connection connection) {
        this.userRepo = new UserRepositoryImpl();
        this.groupRepo = new GroupRepositoryImpl();
        this.userGroupRepo = new UserGroupRepositoryImpl(connection);
        this.storage = new SessionStorageImpl();
    }

    public synchronized SessionStorage getStorage() {
        return storage;
    }

    public static synchronized void initialize(EntrypointConfig.AuthConfig config) {

        if (instance != null) {
            throw new IllegalStateException("AuthenticationManager is already initialized");
        }
        try {
            instance = new AuthenticationManager(ConnectionManager.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Could retrieve connection to metadata: " + e.getMessage());
        }
    }

    public static synchronized AuthenticationManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AuthenticationManager should be initialized");
        }
        return instance;
    }

    public User authenticateUser(String username, String password) throws Exception {
        System.out.println("retrieving user: " + username);
        User user = this.retrieveUser(username);
        System.out.println("user found: " + user.toString());
        String passwordHash = MetadataUtils.hashPassword(password);
        if (!user.getPassword().equals(passwordHash)) {
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

}
