package pl.piotrtrybala.database.executor.sync.results;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.security.AuthenticationManager;
import pl.piotrtrybala.database.security.session.Session;
import pl.piotrtrybala.database.executor.Result;
import pl.piotrtrybala.database.command.types.PasswordCommand;
import pl.piotrtrybala.database.storage.types.User;

public class PasswordResult implements Result {

    private static final Logger LOGGER = LogManager.getLogger(PasswordResult.class.getName());

    private final PasswordCommand command;

    public PasswordResult(PasswordCommand command) {
        this.command = command;
    }

    @Override
    public char[] getResult() {

        Session session = AuthenticationManager.getInstance().getStorage().retrieve(null);
        if (session == null) {
            return "User is not authenticated".toCharArray();
        }

        String password = command.getPassword();
        User user = session.getUser();
        LOGGER.info("changing password for user: {}", user.getUsername());

        user.setPassword(password);

        try {
            AuthenticationManager.getInstance().getUserRepo().update(user);
        } catch (Exception e) {
            String message = "failed to change password: " + e.getMessage();
            return message.toCharArray();
        }
        return "OK".toCharArray();
    }
}
