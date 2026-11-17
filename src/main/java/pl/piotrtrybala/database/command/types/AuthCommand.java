package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandUtils;
import pl.piotrtrybala.database.command.enums.QueryType;

import java.util.List;

public class AuthCommand implements Command {

    private final String username;
    private final String password;

    public AuthCommand(String query) {
        List<String> parts = CommandUtils.preprocess(query);

        int USERNAME_INDEX = 1;
        this.username = parts.get(USERNAME_INDEX).trim();
        int PASSWORD_INDEX = 2;
        this.password = parts.get(PASSWORD_INDEX).trim();
    }

    public AuthCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthQuery{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.AUTH;
    }
}

