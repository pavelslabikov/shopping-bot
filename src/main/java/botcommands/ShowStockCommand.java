package botcommands;

import app.Customer;
import storages.IStorage;
import java.util.HashMap;

public class ShowStockCommand implements IBotCommand {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public ShowStockCommand(HashMap<Integer, Customer> customers, IStorage storage) {
        this.storage = storage;
        this.customers = customers;
    }

    @Override
    public String getCommandIdentifier() {
        return "/stock";
    }

    @Override
    public String getDescription() {
        return "Show items in stock";
    }

    @Override
    public String execute(Integer userId, String[] args) {
        if (!customers.containsKey(userId))
            return "\u274C Прежде чем вводить данную команду, начните работу с ботом!";

        var currentStock = new StringBuilder("\uD83D\uDCE6 Список всех товаров в наличии:\n\n");
        var storageItems = storage.getAllItems();
        for (var item : storageItems)
            currentStock.append(String.format("%s\n", item));

        return currentStock.toString();
    }
}