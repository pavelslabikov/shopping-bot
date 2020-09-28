package botcommands;

import main.Main;
import main.User;


public class ShowCartCommand extends BotCommand {
    @Override
    public void execute(User sender, String [] args) {
        Main.printMessage(sender.cart.getItems());
    }
}

