package botcommands;

import models.BotMessage;
import models.Customer;
import app.UserState;
import models.Keyboard;
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
        if (!customers.containsKey(userId)) {
            logger.info("User {} has become a new customer", userId);
            customers.put(userId, new Customer(userId));
            resultMessage.setKeyboard(new Keyboard());
        } else {
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