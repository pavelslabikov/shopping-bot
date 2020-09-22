package botcommands;

import main.Main;
import main.User;


public class ShowCartCommand extends BotCommand {
    @Override
    public void execute(User sender, String [] args) {
        if (sender.cart.isEmpty())
            Main.printMessage("Ваша корзина в данный момент пуста!");
        else {
            var items = new StringBuilder("Список товаров в корзине:\n");
            for (var item : sender.cart.keySet())
                items.append(String.format("(%s) — %s шт.\n", item, sender.cart.get(item)));

            Main.printMessage(items.toString());
        }
    }
}
