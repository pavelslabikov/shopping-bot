package botcommands;

import models.BotMessage;
import models.Customer;
import app.UserState;
import storages.IStorage;
import storages.IStorageItem;

import java.util.HashMap;

public class SearchCommand implements IBotCommand {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public SearchCommand(HashMap<Integer, Customer> customers, IStorage storage) {
        this.customers = customers;
        this.storage = storage;
    }

    @Override
    public String getCommandIdentifier() {
        return "/search";
    }

    @Override
    public String getDescription() {
        return "Search for an item in stock";
    }

    @Override
    public BotMessage execute(Integer userId, String[] args) {
        var resultMessage = new BotMessage();
        var customer = customers.get(userId);
        if (customer == null) {
            resultMessage.setText("\u274C Начните работу с ботом - /start!");
            return resultMessage;
        }

        if (customer.getState() == UserState.start) {
            var itemToSearch = String.join(" ", args);
            IStorageItem foundItem;
            if (itemToSearch.matches("\\d+")) {
                logger.info("Trying to find an item with id: {}", itemToSearch);
                var id = Integer.parseInt(itemToSearch);
                foundItem = storage.getItemById(id);
            } else {
                logger.info("Trying to find an item with name: {}", itemToSearch);
                foundItem = storage.getItemByName(itemToSearch);
            }

            if (foundItem == null)
                resultMessage.setText("\u2753 Товар не найден!");
            else {
                resultMessage.setText(String.format("\uD83D\uDD0E Найденный товар:\n%s", foundItem));
                resultMessage.setPhoto(foundItem.getImage());
            }
        }

        return resultMessage;
    }
}

