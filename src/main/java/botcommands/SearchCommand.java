package botcommands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import storages.IStorage;
import storages.IStorageItem;

public class SearchCommand extends BotCommand {
    private final IStorage storage;

    public SearchCommand(String commandIdentifier, String description, IStorage storage) {
        super(commandIdentifier, description);
        this.storage = storage;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var message = new SendMessage();
        message.setChatId(chat.getId());
        String response = "";
        if (strings.length != 1){
            response = "Неверное количество аргументов для команды!\nИспользуйте: /search <ID/NAME>";
        }

        else {
            IStorageItem foundItem;
            if (strings[0].matches("\\d+")) {
                var id = Integer.parseInt(strings[0]);
                foundItem = storage.getItemById(id);
            } else
                foundItem = storage.getItemByName(strings[0]);
            response = foundItem == null
                    ? "Товар не найден!"
                    : String.format("Найденный товар:\n%s", foundItem);
        }
        message.setText(response);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

