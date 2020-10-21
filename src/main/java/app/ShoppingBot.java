package app;

import botcommands.*;
import models.Customer;
import storages.IStorage;
import storages.IStorageItem;
import storages.Storage;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingBot {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public final ArrayList<IBotCommand> availableCommands;

    public ShoppingBot() {
        storage = new Storage();
        customers = new HashMap<>();
        availableCommands = new ArrayList<>();
        registerCommands();
    }

    public void registerCommands() {
        availableCommands.add(new HelpCommand());
        availableCommands.add(new ShowCartCommand(customers));
        availableCommands.add(new ClearCartCommand(customers));
        availableCommands.add(new SearchCommand(customers, storage));
        availableCommands.add(new AddToCartCommand(customers, storage));
        availableCommands.add(new ShowStockCommand(customers, storage));
        availableCommands.add(new StartCommand(customers));
    }

    public String answerUser(int userId , String message) {
        var customer = customers.get(userId);
        if (customer == null)
            return "начните работу с ботом /start";

        var state = customer.getState();
        switch (state) {
            case addId:
                if (message.split(" ").length != 1)
                    return "Необходимо ввести одно слово без пробелов";

                IStorageItem itemToAdd;
                if (message.matches("^\\d+")){
                    var id = Integer.parseInt(message);
                    itemToAdd = storage.getItemById(id);
                } else
                    itemToAdd = storage.getItemByName(message);

                if (itemToAdd == null) {
                    customer.setState(UserState.start);
                    return "❓ Введённое имя товара не был найден на складе!";
                }

                customer.setItemToAdd(itemToAdd);
                customer.setState(UserState.addCount);
                return "Введите количество";

            case addCount:
                var cart = customer.getCart();
                if (message.split(" ").length != 1)
                    return "Необходимо ввести одно слово без пробелов";

                if (!message.matches("^\\d+"))
                    return "необходимо ввести цифры";

                customer.setState(UserState.start);
                return cart.addItem(customer.getItemToAdd(), Integer.parseInt(message));

            case start:
                IStorageItem foundItem;
                var text = message.split(" ");
                if (text[0].matches("\\d+")) {
                    var id = Integer.parseInt(text[0]);
                    foundItem = storage.getItemById(id);
                } else
                    foundItem = storage.getItemByName(text[0]);
                return foundItem == null
                        ? "\u2753 Товар не найден!"
                        : String.format("\uD83D\uDD0E Найденный товар:\n%s", foundItem);
            default:
                return "\u274C Что-то пошло не так!\n" +
                        "Введите /help для просмотра списка всех команд.";
        }
    }
}