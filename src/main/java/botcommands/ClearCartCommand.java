package botcommands;

import models.Customer;
import app.UserState;

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
    public String execute(Integer userId, String[] args) {
        if (!customers.containsKey(userId))
            return "\u274C Прежде чем вводить данную команду, начните работу с ботом!";

        customers.get(userId).setState(UserState.start);
        var currentCustomer = customers.get(userId);
        return currentCustomer.getCart().removeItems();
    }
}

