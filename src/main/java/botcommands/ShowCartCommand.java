package botcommands;

import models.BotMessage;
import models.Customer;
import app.UserState;
import models.Keyboard;

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
        } else {
            customers.get(userId).setState(UserState.start);
            var currentCustomer = customers.get(userId);
            resultMessage.setText(currentCustomer.getCart().getItems());
        }

        return resultMessage;
    }
}

