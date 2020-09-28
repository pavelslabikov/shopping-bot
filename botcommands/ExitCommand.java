package botcommands;

import main.Main;
import main.User;

public class ExitCommand extends BotCommand {
    @Override
    public void execute(User sender, String [] args) {
        Main.printMessage("Завершение работы с ботом...");
        sender.cart.exportContent(String.format("main/logs/user-log%s.txt", sender.getId()));
        System.exit(0);
    }
}
