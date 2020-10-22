package botcommands;

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
    public String execute(Integer userId, String[] args) {
        var customer = customers.get(userId);
        if (customer == null)
            return "\u274C Прежде чем вводить данную команду, начните работу с ботом!";

        if (customer.getState() == UserState.start) {
            customer.setState(UserState.addId);
            return "Введите имя/ID товара без пробелов:";
        }

        if (customer.getState() == UserState.addId) {
            if (args.length != 1)
                return "\u274C Необходимо ввести имя/ID одного товара без пробелов!";

            IStorageItem itemToAdd;
            if (args[0].matches("^\\d+")){
                var id = Integer.parseInt(args[0]);
                itemToAdd = storage.getItemById(id);
            } else
                itemToAdd = storage.getItemByName(args[0]);

            if (itemToAdd == null) {
                customer.setState(UserState.start);
                return "❓ Введённое имя/ID товара не было найдено на складе!";
            }

            customer.setItemToAdd(itemToAdd);
            customer.setState(UserState.addCount);
            return "Введите количество товара:";
        } else {
            if (args.length != 1 || !args[0].matches("^\\d+"))
                return "\u274C Некорректный формат ввода!";

            customer.setState(UserState.start);
            return customer.getCart()
                    .addItem(customer.getItemToAdd(), Integer.parseInt(args[0]));
        }
    }
}