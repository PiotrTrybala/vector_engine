package pl.piotrtrybala.database.command;

public interface CommandHandler {
    boolean canParse(String query) throws CommandException;
    Command parse(String query) throws CommandException;
}
