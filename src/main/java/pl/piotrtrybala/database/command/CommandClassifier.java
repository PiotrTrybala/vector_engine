package pl.piotrtrybala.database.command;

import pl.piotrtrybala.database.command.enums.Keyword;

import java.util.List;

public class CommandClassifier {

    public static boolean isHandshakeQuery(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);
        return tokens.size() == 1 && Keyword.match(tokens.getFirst(), Keyword.HANDSHAKE);
    }

    public static boolean isAuthQuery(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);
        return tokens.size() == 3 && Keyword.match(tokens.getFirst(), Keyword.AUTH);
    }

    public static boolean isCreateCollectionQuery(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);
        return tokens.size() == 3 && Keyword.match(tokens.getFirst(), Keyword.CREATE) && Keyword.match(tokens.get(1), Keyword.COLLECTION);
    }

    public static boolean isInsertQuery(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);
        return tokens.size() == 8 && Keyword.match(tokens.getFirst(), Keyword.INSERT)
                && Keyword.match(tokens.get(1), Keyword.INTO)
                && Keyword.match(tokens.get(3), Keyword.TYPE)
                && Keyword.match(tokens.get(5), Keyword.BEGIN)
                && Keyword.match(tokens.get(7), Keyword.END);
    }

    public static boolean isUpdateQuery(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);

        return Keyword.match(tokens.getFirst(), Keyword.UPDATE) &&
                Keyword.match(tokens.get(2), Keyword.TYPE) &&
                Keyword.match(tokens.get(4), Keyword.VALUE);
    }

    public static boolean isSelectQuery(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);

        int selectIndex = CommandUtils.findKeywordIndex(tokens, Keyword.SELECT);

        return selectIndex == 0 && tokens.size() >= 4;
    }

    public static boolean isShowQuery(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);
        return Keyword.match(tokens.getFirst(), Keyword.SHOW) && tokens.size() == 2;
    }

    public static boolean isUseQuery(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);
        return Keyword.match(tokens.getFirst(), Keyword.USE) && tokens.size() == 2;
    }

    public static boolean isPasswordCommand(String query) throws CommandException {
        List<String> tokens = CommandUtils.preprocess(query);
        return Keyword.match(tokens.getFirst(), Keyword.PASSWORD) && tokens.size() == 2;
    }

}
