package pl.piotrtrybala.engine.executor.impl.result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.auth.session.Session;
import pl.piotrtrybala.engine.executor.types.ExecutorResult;
import pl.piotrtrybala.engine.query.types.AuthQuery;

public class AuthResult implements ExecutorResult {

    private static final Logger LOGGER = LogManager.getLogger(AuthResult.class.getName());

    private final AuthQuery query;
    private final AuthManager instance;

    public AuthResult(AuthQuery query) {
        this.query = query;
        this.instance = AuthManager.getInstance();
    }

    @Override
    public char[] getResult() {
        try {
            LOGGER.info("checking {} with {}", query.getUsername(), query.getPassword());
            User user = this.instance.authenticateUser(
                    query.getUsername(),
                    query.getPassword()
            );

            Session session = this.instance.getStorage().create(user);
            LOGGER.info("created new session: {}", session);
        } catch (Exception e) {
            return "Failed to authenticate user".toCharArray();
        }
        return "OK".toCharArray();
    }
}
