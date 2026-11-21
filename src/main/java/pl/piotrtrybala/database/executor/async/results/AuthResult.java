package pl.piotrtrybala.database.executor.async.results;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.auth.AuthenticationManager;
import pl.piotrtrybala.database.command.types.AuthCommand;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.storage.types.User;

import java.util.Optional;

public class AuthResult implements AsyncResult {

    private static final Logger LOGGER = LogManager.getLogger(AuthResult.class.getName());

    private final AuthCommand command;

    public AuthResult(AuthCommand command) {
        this.command = command;
    }



    @Override
    public char[] getResult() {

        try {
            Optional<User> user = AuthenticationManager.getInstance().authenticateUser(
                    command.getUsername(),
                    command.getPassword()
            );
            if (user.isEmpty()) {
                return "User is not found".toCharArray();
            }
            context.createSession(user.get());

        } catch (Exception e) {
            LOGGER.error("failed to authenticate user: {}", e.getMessage());
            return "Failed to authenticate user".toCharArray();
        }
        return "OK".toCharArray();
    }
}
