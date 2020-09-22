package botcommands;

import main.Main;
import main.User;
import storages.IStorage;

public class ShowStockCommand extends BotCommand {
    private final IStorage storage;

    public ShowStockCommand(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(User sender, String [] args) {
        var currentStock = new StringBuilder("Список всех товаров в наличии:\n\n");
        var storageItems = storage.getAllItems();
        for (var item : storageItems)
            currentStock.append(String.format("%s\n", item));

        Main.printMessage(currentStock.toString());
    }
}