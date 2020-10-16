package botcommands;

import app.Customer;
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
        return "null";
    }

    @Override
    public String execute(Integer userId, String[] args) {
        var cart = customers.get(userId).getCart();
        IStorageItem itemToAdd;

        if (args.length != 2)
            return "\u274C Неверное количество аргументов для команды!\n" +
                    "Используйте: /add <ID/NAME> <amount>";

        if (!args[1].matches("^\\d+"))
            return "\u274C Некорректный формат ввода!";

        if (args[0].matches("^\\d+")) {
            var id = Integer.parseInt(args[0]);
            itemToAdd = storage.getItemById(id);
            if (itemToAdd == null)
                return "❓ Введённый идентификатор товара не был найден на складе!";

            return cart.addItem(itemToAdd, Integer.parseInt(args[1]), storage.getItemAmount(id));
        }

        itemToAdd = storage.getItemByName(args[0]);
        if (itemToAdd == null)
            return "❓ Введённое имя товара не было найдено на складе!";

        var id = itemToAdd.getId();
        return cart.addItem(itemToAdd, Integer.parseInt(args[1]), storage.getItemAmount(id));
    }
}