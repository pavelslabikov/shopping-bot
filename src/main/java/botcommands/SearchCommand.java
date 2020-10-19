package botcommands;

import app.Customer;
import storages.IStorage;
import storages.IStorageItem;

import java.util.HashMap;

public class SearchCommand implements IBotCommand {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public SearchCommand(HashMap<Integer, Customer> customers, IStorage storage) {
        this.customers = customers;
        this.storage = storage;
    }

    @Override
    public String getCommandIdentifier() {
        return "/search";
    }

    @Override
    public String getDescription() {
        return "Search for an item in stock";
    }

    @Override
    public String execute(Integer userId, String[] args) {
        if (!customers.containsKey(userId))
            return "\u274C Прежде чем вводить данную команду, начните работу с ботом!";

        if (args.length != 1)
            return "\u274C Неверное количество аргументов для команды!\nИспользуйте: /search <ID/NAME>";

        IStorageItem foundItem;
        if (args[0].matches("\\d+")) {
            logger.info("Trying to find an item with id: {}", args[0]);
            var id = Integer.parseInt(args[0]);
            foundItem = storage.getItemById(id);
        } else {
            logger.info("Trying to find an item with name: {}", args[0]);
            foundItem = storage.getItemByName(args[0]);
        }

        return foundItem == null
                ? "\u2753 Товар не найден!"
                : String.format("\uD83D\uDD0E Найденный товар:\n%s", foundItem);
    }
}

