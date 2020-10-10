package botcommands;

import app.Customer;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import storages.IStorage;
import storages.IStorageItem;

import java.util.HashMap;

public class AddToCartCommand extends ShoppingCommand {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public AddToCartCommand(HashMap<Integer, Customer> customers, IStorage storage) {
        super("/add", "descr");
        this.storage = storage;
        this.customers = customers;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] args) {
        var cart = customers.get(user.getId()).getCart();
        IStorageItem itemToAdd;

        if (args.length != 2) {
            execute(absSender, chat, "\u274C Неверное количество аргументов для команды!\n" +
                    "Используйте: /add <ID/NAME> <amount>");
            return;
        }

        if (!args[1].matches("^\\d+")) {
            execute(absSender, chat, "\u274C Некорректный формат ввода!");
            return;
        }

        if (args[0].matches("^\\d+")) {
            var id = Integer.parseInt(args[0]);
            itemToAdd = storage.getItemById(id);
            if (itemToAdd == null) {
                execute(absSender, chat, "❓ Введённый идентификатор товара не был найден на складе!");
                return;
            }

            var response = cart.addItem(itemToAdd, Integer.parseInt(args[1]), storage.getItemAmount(id));
            execute(absSender, chat, response);
            return;
        }

        itemToAdd = storage.getItemByName(args[0]);
        if (itemToAdd == null) {
            execute(absSender, chat, "❓ Введённое имя товара не было найдено на складе!");
            return;
        }

        var id = itemToAdd.getId();
        var response = cart.addItem(itemToAdd, Integer.parseInt(args[1]), storage.getItemAmount(id));
        execute(absSender, chat, response);
    }
}