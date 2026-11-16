package pl.piotrtrybala.database.executor.impl.result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.storage.types.User;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.executor.types.Result;
import pl.piotrtrybala.database.query.types.AuthCommand;

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
        try {
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
