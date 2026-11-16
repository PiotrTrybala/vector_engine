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

import javax.swing.text.html.Option;
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
        this.userGroupRepo = new UserGroupRepositoryImpl(this.userRepo, this.groupRepo);
        this.storage = new SessionStorageImpl();
    }

    public SessionStorage getStorage() {
        return storage;
    }

    public GroupRepository getGroupRepo() {
        return groupRepo;
    }

    public UserRepository getUserRepo() {
        return userRepo;
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

    public Optional<User> authenticateUser(String username, String password) throws Exception {
        System.out.println("retrieving user: " + username);
        Optional<User> user = this.retrieveUser(username);
        System.out.println("user found: " + user.toString());
        String passwordHash = MetadataUtils.hashPassword(password);
        if (user.isPresent() && !user.get().getPassword().equals(passwordHash)) {
            throw new Exception("failed to authenticate user");
        }

        return user;
    }

    public Optional<User> retrieveUser(String username) throws RepositoryException {
        Optional<User> user = this.userRepo.findByUsername(username);
        if (user.isEmpty()) return Optional.empty();

        Optional<Group> group = this.userGroupRepo.getGroupByUserId(user.get().getId());
        if (group.isEmpty()) return user;

        user.get().setGroup(group.get());
        return user;
    }

}
