package botcommands;

import app.Customer;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import storages.IStorage;

import java.util.HashMap;

public class ShowStockCommand extends ShoppingCommand {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public ShowStockCommand(HashMap<Integer, Customer> customers, IStorage storage) {
        super("/stock", "show items in stock");
        this.storage = storage;
        this.customers = customers;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (!customers.containsKey(user.getId())) {
            execute(absSender, chat, "\u274C Прежде чем вводить данную команду, начните работу с ботом!");
            return;
        }

        var currentStock = new StringBuilder("\uD83D\uDCE6 Список всех товаров в наличии:\n\n");
        var storageItems = storage.getAllItems();
        for (var item : storageItems)
            currentStock.append(String.format("%s\n", item));

        execute(absSender, chat, currentStock.toString());
    }
}