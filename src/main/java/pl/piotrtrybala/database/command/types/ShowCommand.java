package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandUtils;
import pl.piotrtrybala.database.command.enums.UseTargetType;
import pl.piotrtrybala.database.command.enums.CommandType;

import java.util.List;

public class ShowCommand implements Command {

    private UseTargetType useTargetType;

    public ShowCommand(String query) {
        List<String> parts = CommandUtils.preprocess(query);
        this.useTargetType = UseTargetType.valueOf(parts.getLast());
    }

    @Override
    public CommandType getQueryType() {
        return CommandType.SHOW;
    }

    public UseTargetType getEntityType() {
        return useTargetType;
    }
}
