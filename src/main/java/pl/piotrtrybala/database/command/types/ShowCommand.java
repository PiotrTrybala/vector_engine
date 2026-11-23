package pl.piotrtrybala.database.command.types;

import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandUtils;
import pl.piotrtrybala.database.command.enums.UseTarget;
import pl.piotrtrybala.database.command.enums.CommandType;

import java.util.List;

public class ShowCommand implements Command {

    private final UseTarget useTarget;

    public ShowCommand(String query) {
        List<String> parts = CommandUtils.preprocess(query);
        this.useTarget = UseTarget.valueOf(parts.getLast());
    }

    @Override
    public CommandType getQueryType() {
        return CommandType.SHOW;
    }

    public UseTarget getUseTarget() {
        return useTarget;
    }
}
