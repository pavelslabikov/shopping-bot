package app;

import botcommands.*;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import storages.IStorage;
import storages.Storage;

import java.util.HashMap;

public class ShoppingBot extends TelegramLongPollingCommandBot{
    public final HashMap<Integer, Customer> customers;

    private final IStorage storage;

    public ShoppingBot() {
        storage = new Storage();
        customers = new HashMap<>();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (!update.hasMessage())
            return;

        var currentChatId = update.getMessage().getChatId();
        sendReplyToUser(currentChatId, "\u274C Что-то пошло не так!\n" +
                "Введите /help для просмотра списка всех команд.");
    }

    public void registerCommands() {
        register(new HelpCommand());
        register(new ShowCartCommand(customers));
        register(new ClearCartCommand(customers));
        register(new SearchCommand(customers, storage));
        register(new AddToCartCommand(customers, storage));
        register(new ShowStockCommand(customers, storage));
        register(new StartCommand(customers));
    }

    @Override
    public String getBotToken() {
        return System.getenv("TOKEN");
    }

    @Override
    public String getBotUsername() {
        return "javaShoppingBot";
    }

    private void sendReplyToUser(Long chatId, String text) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}