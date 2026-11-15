package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandUtils;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class AuthQuery implements Command {

    private final String username;
    private final String password;

    public AuthQuery(String query) {
        List<String> parts = CommandUtils.preprocess(query);

        int USERNAME_INDEX = 1;
        this.username = parts.get(USERNAME_INDEX).trim();
        int PASSWORD_INDEX = 2;
        this.password = parts.get(PASSWORD_INDEX).trim();
    }

    public AuthQuery(String username, String password) {
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

