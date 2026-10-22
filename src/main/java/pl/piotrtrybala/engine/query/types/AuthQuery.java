package pl.piotrtrybala.engine.query.types;

import pl.piotrtrybala.engine.query.Query;
import pl.piotrtrybala.engine.query.QueryMatcher;

import java.util.List;

public class AuthQuery implements Query {

    private static int USERNAME_INDEX = 1;
    private static int PASSWORD_INDEX = 2;

    private final String username;
    private final String password;

    public AuthQuery(String query) {
        List<String> parts = QueryMatcher.preprocess(query);

        this.username = parts.get(USERNAME_INDEX).trim();
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
}
