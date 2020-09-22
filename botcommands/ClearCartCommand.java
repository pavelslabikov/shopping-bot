package botcommands;

import main.ShoppingBot;
import main.User;


public class ClearCartCommand extends BotCommand {
    public ClearCartCommand(ShoppingBot botContext) {
        super(botContext);
    }

    @Override
    public void execute(User sender, String [] args) {
        sender.cart.clear();
        sender.receiveResponse("Корзина успешно очищена!");
    }
}

