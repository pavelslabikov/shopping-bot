package botcommands;

import main.ShoppingBot;
import main.User;

public class ShowStockCommand extends BotCommand {
    public ShowStockCommand(ShoppingBot botContext) {
        super(botContext);
    }

    @Override
    public void execute(User sender, String [] args) {
        var currentStock = new StringBuilder("Список всех товаров в наличии:\n\n");
        var storageItems = bot.storage.getAllItems();
        for (var item : storageItems)
            currentStock.append(String.format("%s\n", item));

        sender.receiveResponse(currentStock.toString());
    }
}