package botcommands;

import main.ShoppingBot;
import main.User;

public class SearchCommand extends BotCommand {
    public SearchCommand(ShoppingBot botContext) {
        super(botContext);
    }

    @Override
    public void execute(User sender, String[] args) {
        if (args.length != 2)
            sender.receiveResponse("Неверное количество аргументов для команды!\nИспользуйте: /search <ID>");
        else {
            var foundItem = bot.storage.getItem(Integer.parseInt(args[1]));
            var response = foundItem == null
                    ? "Товар не найден!"
                    : String.format("Найденный товар:\n%s", foundItem);
            sender.receiveResponse(response);
        }
    }
}

