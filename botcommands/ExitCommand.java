package botcommands;

import main.ShoppingBot;
import main.User;

public class ExitCommand extends BotCommand {
    public ExitCommand(ShoppingBot botContext) {
        super(botContext);
    }

    @Override
    public void execute(User sender, String [] args) {
        sender.receiveResponse("Завершение работы с ботом...");
        sender.exportCartContent();
        System.exit(0);
    }
}
