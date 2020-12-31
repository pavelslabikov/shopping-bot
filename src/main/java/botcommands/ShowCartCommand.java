package botcommands;

import models.BotMessage;
import models.Customer;
import app.UserState;
import models.Keyboard;
import storages.IStorageItem;

import java.util.Collection;
import java.util.HashMap;


public class ShowCartCommand implements IBotCommand {
    private final HashMap<Integer, Customer> customers;

    public ShowCartCommand(HashMap<Integer, Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String getCommandIdentifier() {
        return "/cart";
    }

    @Override
    public String getDescription() {
        return "null";
    }

    @Override
    public BotMessage execute(Integer userId, String[] args) {
        var resultMessage = new BotMessage();
        if (!customers.containsKey(userId)) {
            logger.info("User {} has become a new customer", userId);
            customers.put(userId, new Customer(userId));
            resultMessage.setKeyboard(new Keyboard());
        }

        customers.get(userId).setState(UserState.start);
        var currentCustomer = customers.get(userId);
        var allItems = currentCustomer.getCart().getItems();
        resultMessage.setText(getItemsRepresentation(allItems));
        return resultMessage;
    }

    private String getItemsRepresentation(Collection<IStorageItem> items) {
        if (items.isEmpty())
            return "Ваша корзина в данный момент пуста";
        var result = new StringBuilder("Список товаров в корзине");
        for (var item : items)
            result.append(item.toString());
        return result.toString();
    }
}

