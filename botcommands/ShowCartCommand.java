package botcommands;

import main.ShoppingBot;
import main.User;


public class ShowCartCommand extends BotCommand {
    public ShowCartCommand(ShoppingBot botContext) {
        super(botContext);
    }

    @Override
    public void execute(User sender, String [] args) {
        if (sender.cart.isEmpty())
            sender.receiveResponse("Ваша корзина в данный момент пуста!");
        else {
            var items = new StringBuilder("Список товаров в корзине:\n");
            for (var item : sender.cart.keySet())
                items.append(String.format("(%s) — %s шт.\n", item, sender.cart.get(item)));

            sender.receiveResponse(items.toString());
        }
    }
}
