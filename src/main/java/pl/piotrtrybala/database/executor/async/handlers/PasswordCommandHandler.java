package pl.piotrtrybala.database.executor.async.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.types.PasswordCommand;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.PasswordResult;
import pl.piotrtrybala.database.security.AuthenticationManager;
import pl.piotrtrybala.database.security.middleware.AuthMiddleware;
import pl.piotrtrybala.database.security.middleware.Middleware;
import pl.piotrtrybala.database.storage.RepositoryException;

public class PasswordCommandHandler implements CommandHandler{

    private static final Logger LOGGER = LogManager.getLogger(PasswordCommandHandler.class.getName());

    @Override
    public AsyncResult handle(Context context, Command command) {
        PasswordCommand passwordCommand = (PasswordCommand) command;

        try {
            LOGGER.info("changing password for {}", context.getSession().getUser().getUsername());
            AuthenticationManager.getInstance().updatePassword(
                    context.getSession().getUser(),
                    passwordCommand.getPassword()
            );
        } catch(RepositoryException e) {
            LOGGER.error("Could not change user password: {}", e.getMessage());
            return ("Could not change password: " + e.getMessage())::toCharArray;
        }

        return new PasswordResult();
    }

    @Override
    public Middleware<Context> getMiddleware() {
        return new AuthMiddleware();
    }
}
