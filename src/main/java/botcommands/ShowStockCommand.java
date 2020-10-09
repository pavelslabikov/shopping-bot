package botcommands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import storages.IStorage;

public class ShowStockCommand extends BotCommand {
    private final IStorage storage;

    public ShowStockCommand(String commandIdentifier, String description, IStorage storage) {
        super(commandIdentifier, description);
        this.storage = storage;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var currentStock = new StringBuilder("Список всех товаров в наличии:\n\n");
        var storageItems = storage.getAllItems();
        for (var item : storageItems)
            currentStock.append(String.format("%s\n", item));

        var message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText(currentStock.toString());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}