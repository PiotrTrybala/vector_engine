package pl.piotrtrybala.database.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.EntrypointConfig;
import pl.piotrtrybala.database.security.session.Session;
import pl.piotrtrybala.database.security.session.impl.SessionManagerImpl;
import pl.piotrtrybala.database.security.users.UserManagerImpl;
import pl.piotrtrybala.database.storage.MetadataUtils;
import pl.piotrtrybala.database.storage.types.User;

import java.util.Optional;

public class AuthenticationManager {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationManager.class.getName());

    private final SessionManager<Session> sessionManager;
    private final UserManager<User> userManager;

    private static AuthenticationManager instance;

    private AuthenticationManager() {
        this.sessionManager = new SessionManagerImpl();
        this.userManager = new UserManagerImpl();
    }

    public static synchronized void initialize(EntrypointConfig.AuthConfig config) {
        if (instance != null) {
            throw new IllegalStateException("AuthenticationManager is already initialized");
        }
        instance = new AuthenticationManager();
    }

    public static synchronized AuthenticationManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AuthenticationManager should be initialized");
        }
        return instance;
    }

    public Session auth(String username, String password) throws Exception {
        Optional<User> user = this.userManager.retrieve(username);
        String passwordHash = MetadataUtils.hashPassword(password);

        if (user.isEmpty() || !user.get().getPassword().equals(passwordHash)) {
            throw new Exception("failed to authenticate user");
        }

        Optional<Session> session = this.sessionManager.create(user.get());
        if (session.isEmpty()) {
            throw new Exception("failed to authenticate user session");
        }

        return session.get();
    }

    public Optional<Session> retrieve(String sessionToken) throws Exception {
        return this.sessionManager.retrieve(sessionToken);
    }
}
