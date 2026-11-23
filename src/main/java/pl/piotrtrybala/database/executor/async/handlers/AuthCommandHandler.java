package pl.piotrtrybala.database.executor.async.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.types.AuthCommand;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.executor.async.results.AuthResult;
import pl.piotrtrybala.database.storage.types.User;

import java.util.Optional;

public class AuthCommandHandler implements CommandHandler {

    private static final Logger LOGGER = LogManager.getLogger(AuthCommandHandler.class.getName());

    @Override
    public AsyncResult handle(Context context, Command command) {
        AuthCommand authCommand = (AuthCommand) command;

        try {



        } catch(Exception e) {
            LOGGER.error("failed to authenticate user: {}", e.getMessage());
            return new AsyncResult() {
                @Override
                public char[] getResult() {
                    return "Failed to authenticate user".toCharArray();
                }
            };
        }


        return new AuthResult();
    }
}
