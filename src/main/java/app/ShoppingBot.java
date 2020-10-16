package app;

import botcommands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import storages.IStorage;
import storages.Storage;

import java.util.HashMap;

public class ShoppingBot extends TelegramLongPollingCommandBot {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingBot.class);

    private final IStorage storage;
    private final HashMap<Integer, Customer> customers;

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
        register(new CommandAdapter(new HelpCommand()));
        register(new CommandAdapter(new ShowCartCommand(customers)));
        register(new CommandAdapter(new ClearCartCommand(customers)));
        register(new CommandAdapter(new SearchCommand(customers, storage)));
        register(new CommandAdapter(new AddToCartCommand(customers, storage)));
        register(new CommandAdapter(new ShowStockCommand(customers, storage)));
        register(new CommandAdapter(new StartCommand(customers)));
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
            logger.error("Caught exception with following stacktrace:", e);
        }
    }
}