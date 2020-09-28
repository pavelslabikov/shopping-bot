package botcommands;

import main.Main;
import main.User;


public class ClearCartCommand extends BotCommand {
    @Override
    public void execute(User sender, String [] args) {
        sender.cart.removeItems();
        Main.printMessage("Корзина успешно очищена!");
    }
}

