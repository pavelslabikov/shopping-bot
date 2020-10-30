package app;

import botcommands.*;
import models.Customer;
import storages.IStorage;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingBot {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public final ArrayList<IBotCommand> availableCommands;

    public ShoppingBot(IStorage storage) {
        this.storage = storage;
        customers = new HashMap<>();
        availableCommands = new ArrayList<>();
        registerCommands();
    }

    public void registerCommands() {
        availableCommands.add(new HelpCommand());
        availableCommands.add(new ShowCartCommand(customers));
        availableCommands.add(new ClearCartCommand(customers));
        availableCommands.add(new AddToCartCommand(customers, storage));
        availableCommands.add(new ShowStockCommand(customers, storage));
        availableCommands.add(new StartCommand(customers));
    }

    public String answerUser(int userId , String message) {
        var customer = customers.get(userId);
        if (customer == null)
            return "\u274C Прежде чем вводить данную команду, начните работу с ботом - /start";

        if (message.startsWith("/"))
            return "\u274C Неизвестная команда!";

        return switch (customer.getState()) {
            case addId, addCount -> new AddToCartCommand(customers, storage)
                    .execute(customer.getId(), message.split(" "));

            case start -> new SearchCommand(customers, storage)
                    .execute(customer.getId(), message.split(" "));
        };
    }
}