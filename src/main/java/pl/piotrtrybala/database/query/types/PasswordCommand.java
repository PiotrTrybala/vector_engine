package pl.piotrtrybala.database.query.types;

import pl.piotrtrybala.database.query.Command;
import pl.piotrtrybala.database.query.CommandUtils;
import pl.piotrtrybala.database.query.enums.QueryType;

import java.util.List;

public class PasswordCommand implements Command {

    private final String password;

    public PasswordCommand(String command) {
        List<String> tokens = CommandUtils.preprocess(command);

        this.password = tokens.get(1).trim();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "PasswordCommand{" +
                "password='" + password + '\'' +
                '}';
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.PASSWORD;
    }
}
