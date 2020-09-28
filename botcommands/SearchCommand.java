package botcommands;

import main.Main;
import main.User;
import storages.IStorage;
import storages.IStorageItem;

public class SearchCommand extends BotCommand {
    private final IStorage storage;

    public SearchCommand(IStorage storage) {
        this.storage = storage;
    }


    @Override
    public void execute(User sender, String[] args) {
        if (args.length != 2) {
            Main.printMessage("Неверное количество аргументов для команды!\nИспользуйте: /search <ID/NAME>");
            return;
        }

        IStorageItem foundItem;
        if (args[1].matches("\\d+")) {
            var id = Integer.parseInt(args[1]);
            foundItem = storage.getItemById(id);
        }
        else
            foundItem = storage.getItemByName(args[1]);
        var response = foundItem == null
                ? "Товар не найден!"
                : String.format("Найденный товар:\n%s", foundItem);
        Main.printMessage(response);
    }
}

