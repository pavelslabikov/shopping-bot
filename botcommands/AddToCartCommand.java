package botcommands;

import main.Main;
import storages.*;
import main.User;

public class AddToCartCommand extends BotCommand {
    private final IStorage storage;

    public AddToCartCommand(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(User sender, String [] args) {
        if (args.length != 3) {
            Main.printMessage("Неверное количество аргументов для команды!\nИспользуйте: /add <ID> <amount>");
            return;
        }

        IStorageItem itemToAdd;
        int id;
        try {
            id = Integer.parseInt(args[1]);
            itemToAdd = storage.getItemById(id);
            if (itemToAdd == null) {
                Main.printMessage("Введённый идентефикатор товара не был найден на складе!");
                return;
            }
        }

        catch (NumberFormatException e) {
            var name = args[1];
            itemToAdd = storage.getItemByName(name);
            id = itemToAdd.getId();
            if (itemToAdd == null) {
                Main.printMessage("Введённое имя товара не был найден на складе!");
                return;
            }
        }

        if (sender.cart.containsKey(itemToAdd)) {
            Main.printMessage("Данный товар уже находится в корзине!");
            return;
        }

        try {
            Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e) {
            Main.printMessage("Некорректный формат ввода!");
            return;
        }

        var amountToAdd = Integer.parseInt(args[2]);
        if (amountToAdd > storage.getItemAmount(id) || amountToAdd <= 0) {
            Main.printMessage("Такого количества товара нет в наличии / Некорректное число.");
            return;
        }

        sender.cart.put(itemToAdd, amountToAdd);
        Main.printMessage("Товар успешно добавлен в корзину!");
    }
}