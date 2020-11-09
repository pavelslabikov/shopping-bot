package botcommands;

import models.BotMessage;
import models.Customer;
import app.UserState;
import models.Keyboard;

import java.util.HashMap;

public class ClearCartCommand implements IBotCommand {
    private final HashMap<Integer, Customer> customers;

    public ClearCartCommand(HashMap<Integer, Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String getCommandIdentifier() {
        return "/clear";
    }

    @Override
    public String getDescription() {
        return "Clear cart";
    }

    @Override
    public BotMessage execute(Integer userId, String[] args) {
        var resultMessage = new BotMessage();
        if (!customers.containsKey(userId)) {
            logger.info("User {} has become a new customer", userId);
            customers.put(userId, new Customer(userId));
            resultMessage.setKeyboard(new Keyboard());
        }
        else {
            customers.get(userId).setState(UserState.start);
            var currentCustomer = customers.get(userId);
            resultMessage.setText(currentCustomer.getCart().removeItems());
        }

        return resultMessage;
    }
}

