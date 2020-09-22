package botcommands;

import main.Main;
import main.User;
import storages.IStorage;

public class SearchCommand extends BotCommand {
    private final IStorage storage;

    public SearchCommand(IStorage storage) {
        this.storage = storage;
    }


    @Override
    public void execute(User sender, String[] args) {
        if (args.length != 2)
            Main.printMessage("Неверное количество аргументов для команды!\nИспользуйте: /search <ID>");
        else {
            var foundItem = storage.getItem(Integer.parseInt(args[1]));
            var response = foundItem == null
                    ? "Товар не найден!"
                    : String.format("Найденный товар:\n%s", foundItem);
            Main.printMessage(response);
        }
    }
}

