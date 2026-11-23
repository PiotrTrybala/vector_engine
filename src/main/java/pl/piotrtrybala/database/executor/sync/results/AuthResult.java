package pl.piotrtrybala.database.executor.sync.results;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.security.AuthenticationManager;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.security.session.Session;
import pl.piotrtrybala.database.executor.Result;
import pl.piotrtrybala.database.command.types.AuthCommand;

import java.util.Optional;

public class AuthResult implements Result {

    private static final Logger LOGGER = LogManager.getLogger(AuthResult.class.getName());

    private final AuthCommand query;
    private final AuthenticationManager instance;

    public AuthResult(AuthCommand query) {
        this.query = query;
        this.instance = AuthenticationManager.getInstance();
    }

    @Override
    public char[] getResult() {
//        try {
//            Optional<User> user = this.instance.authenticateUser(
//                    query.getUsername(),
//                    query.getPassword()
//            );
//            if (user.isEmpty()) {
//                return "User is not found".toCharArray();
//            }
//
//            Session session = this.instance.getStorage().create(user.get());
//            LOGGER.info("created new session: {}", session);
//        } catch (Exception e) {
//            LOGGER.error("failed to authenticate user: {}", e.getMessage());
//            return "Failed to authenticate user".toCharArray();
//        }
        return "OK".toCharArray();
    }
}
