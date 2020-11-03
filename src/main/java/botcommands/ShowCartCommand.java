package botcommands;

import models.BotMessage;
import models.Customer;
import app.UserState;

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
        if (!customers.containsKey(userId))
            resultMessage.setText("\u274C Начните работу с ботом - /start!");
        else {
            customers.get(userId).setState(UserState.start);
            var currentCustomer = customers.get(userId);
            resultMessage.setText(currentCustomer.getCart().getItems());
        }

        return resultMessage;
    }
}

