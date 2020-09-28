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
        if (!areValid(args))
            return;

        IStorageItem itemToAdd;
        if (args[1].matches("^\\d+")) {
            var id = Integer.parseInt(args[1]);
            itemToAdd = storage.getItemById(id);
            if (itemToAdd == null) {
                Main.printMessage("Введённый идентификатор товара не был найден на складе!");
                return;
            }

            sender.cart.addItem(itemToAdd, Integer.parseInt(args[2]), storage.getItemAmount(id));
        }
        else {
            itemToAdd = storage.getItemByName(args[1]);
            if (itemToAdd == null) {
                Main.printMessage("Введённое имя товара не было найдено на складе!");
                return;
            }

            var id = itemToAdd.getId();
            sender.cart.addItem(itemToAdd, Integer.parseInt(args[2]), storage.getItemAmount(id));
        }
    }

    private boolean areValid(String [] args) {
        if (args.length != 3) {
            Main.printMessage("Неверное количество аргументов для команды!\nИспользуйте: /add <ID> <amount>");
            return false;
        }

        try {
            Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            Main.printMessage("Некорректный формат ввода!");
            return false;
        }

        return true;
    }
}