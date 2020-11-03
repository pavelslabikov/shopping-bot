package botcommands;

import models.BotMessage;
import models.Customer;
import app.UserState;
import storages.IStorage;
import storages.IStorageItem;

import java.util.HashMap;

public class AddToCartCommand implements IBotCommand {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public AddToCartCommand(HashMap<Integer, Customer> customers, IStorage storage) {
        this.storage = storage;
        this.customers = customers;
    }

    @Override
    public String getCommandIdentifier() {
        return "/add";
    }

    @Override
    public String getDescription() {
        return "Add item to cart";
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
            customer.setState(UserState.addId);
            resultMessage.setText("Введите имя/ID товара:");
        }

        else if (customer.getState() == UserState.addId) {
            var itemName = String.join(" ", args);
            IStorageItem itemToAdd;
            if (itemName.matches("^\\d+")) {
                var id = Integer.parseInt(itemName);
                itemToAdd = storage.getItemById(id);
            } else
                itemToAdd = storage.getItemByName(itemName);

            if (itemToAdd == null) {
                customer.setState(UserState.start);
                resultMessage.setText("❓ Введённое имя/ID товара не было найдено на складе!");
                return resultMessage;
            }

            customer.setItemToAdd(itemToAdd);
            customer.setState(UserState.addCount);
            resultMessage.setText("Введите количество товара:");
        } else {
            if (args.length != 1 || !args[0].matches("^\\d+"))
            {
                resultMessage.setText("\u274C Некорректный формат ввода!");
                return resultMessage;
            }

            customer.setState(UserState.start);
            resultMessage.setText(customer.getCart()
                    .addItem(customer.getItemToAdd(), Integer.parseInt(args[0])));
        }

        return resultMessage;
    }
}