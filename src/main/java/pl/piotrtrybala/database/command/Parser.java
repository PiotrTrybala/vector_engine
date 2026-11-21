package pl.piotrtrybala.database.command;

import java.util.List;

public interface Parser<T> {
    List<T> parse(char[] buffer) throws CommandException;
}
