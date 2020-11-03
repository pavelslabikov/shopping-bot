package botcommands;

import models.BotMessage;
import models.Customer;
import app.UserState;
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
    public BotMessage execute(Integer userId, String[] args) {
        var resultMessage = new BotMessage();
        if (!customers.containsKey(userId))
            resultMessage.setText("\u274C Начните работу с ботом - /start!");
        else {
            customers.get(userId).setState(UserState.start);
            var currentStock = new StringBuilder("\uD83D\uDCE6 Список всех товаров в наличии:\n\n");
            var storageItems = storage.getAllItems();
            for (var item : storageItems)
                currentStock.append(String.format("%s\n", item));
            resultMessage.setText(currentStock.toString());
        }

        return resultMessage;
    }
}