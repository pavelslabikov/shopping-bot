package botcommands;

public interface IBotCommand {
    String getCommandIdentifier();

    String getDescription();

    String execute(Integer userId, String[] args);
}
