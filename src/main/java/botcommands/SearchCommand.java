package botcommands;

import app.Customer;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import storages.IStorage;
import storages.IStorageItem;

import java.util.HashMap;

public class SearchCommand extends ShoppingCommand {
    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

    public SearchCommand(HashMap<Integer, Customer> customers, IStorage storage) {
        super("/search", "description");
        this.customers = customers;
        this.storage = storage;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] args) {
        if (!customers.containsKey(user.getId())) {
            execute(absSender, chat, "\u274C Прежде чем вводить данную команду, начните работу с ботом!");
            return;
        }

        var response = "";
        if (args.length != 1)
            response = "\u274C Неверное количество аргументов для команды!\nИспользуйте: /search <ID/NAME>";
        else {
            IStorageItem foundItem;
            if (args[0].matches("\\d+")) {
                var id = Integer.parseInt(args[0]);
                foundItem = storage.getItemById(id);
            } else
                foundItem = storage.getItemByName(args[0]);
            response = foundItem == null
                    ? "\u2753 Товар не найден!"
                    : String.format("\uD83D\uDD0E Найденный товар:\n%s", foundItem);
        }

        execute(absSender, chat, response);
    }
}

