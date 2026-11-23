package pl.piotrtrybala.database.security;

public class AuthenticationManager {

//    private static final Logger LOGGER = LogManager.getLogger(AuthenticationManager.class.getName());
//
//    private final UserRepository userRepo;
//    private final GroupRepository groupRepo;
//    private final UserGroupRepository userGroupRepo;
//
//    private final SessionStorage storage;
//
//    private static AuthenticationManager instance;
//
//    private AuthenticationManager(Connection connection) {
//        this.userRepo = new UserRepositoryImpl();
//        this.groupRepo = new GroupRepositoryImpl();
//        this.userGroupRepo = new UserGroupRepositoryImpl(this.userRepo, this.groupRepo);
//        this.storage = new SessionStorageImpl();
//    }
//
//    public SessionStorage getStorage() {
//        return storage;
//    }
//
//    public GroupRepository getGroupRepo() {
//        return groupRepo;
//    }
//
//    public UserRepository getUserRepo() {
//        return userRepo;
//    }
//
//    public static synchronized void initialize(EntrypointConfig.AuthConfig config) {
//
//        if (instance != null) {
//            throw new IllegalStateException("AuthenticationManager is already initialized");
//        }
//        try {
//            instance = new AuthenticationManager(ConnectionManager.getConnection());
//        } catch (SQLException e) {
//            throw new RuntimeException("Could retrieve connection to metadata: " + e.getMessage());
//        }
//    }
//
//    public static synchronized AuthenticationManager getInstance() {
//        if (instance == null) {
//            throw new IllegalStateException("AuthenticationManager should be initialized");
//        }
//        return instance;
//    }
//
//    public Optional<User> authenticateUser(String username, String password) throws Exception {
//        LOGGER.info("authenticating user: {}", username);
//        Optional<User> user = this.retrieveUser(username);
//        String passwordHash = MetadataUtils.hashPassword(password);
//        if (user.isPresent() && !user.get().getPassword().equals(passwordHash)) {
//            throw new Exception("failed to authenticate user");
//        }
//
//        return user;
//    }
//
//    public Optional<User> retrieveUser(String username) throws RepositoryException {
//        Optional<User> user = this.userRepo.findByUsername(username);
//        if (user.isEmpty()) return Optional.empty();
//
//        Optional<Group> group = this.userGroupRepo.getGroupByUserId(user.get().getId());
//        if (group.isEmpty()) return user;
//
//        user.get().setGroup(group.get());
//        return user;
//    }

}
