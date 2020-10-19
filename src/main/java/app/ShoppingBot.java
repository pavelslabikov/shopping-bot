package app;

import botcommands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import storages.IStorage;
import storages.IStorageItem;
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

        var message = update.getMessage();
        var currentChatId = message.getChatId();
        var customer = customers.get(message.getFrom().getId());
        if(customer == null) {
            sendReplyToUser(currentChatId, "начните работу с ботом /start");
            return;
        }

        var reply = answerUser(customer, message);
        sendReplyToUser(currentChatId, reply);
    }

    public String answerUser(Customer customer, Message message) {
        var state = customer.getState();
        switch (state){
            case addId:
                var item = message.getText();
                if(item.split(" ").length != 1) {
                    return "Необходимо ввести одно слово без пробелов";
                }

                IStorageItem itemToAdd;
                if (item.matches("^\\d+")){
                    var id = Integer.parseInt(item);
                    itemToAdd = storage.getItemById(id);
                }

                else
                    itemToAdd = storage.getItemByName(item);

                if (itemToAdd == null) {
                    customer.setState(app.state.start);
                    return "❓ Введённое имя товара не был найден на складе!";
                }

                customer.setItemToAdd(itemToAdd);
                customer.setState(app.state.addCount);
                return "Введите количество";
            case addCount:
                var count = message.getText();
                var cart = customer.getCart();
                if(count.split(" ").length != 1) {
                    return "Необходимо ввести одно слово без пробелов";
                }

                if (!count.matches("^\\d+")){
                    return "необходимо ввести цифры";
                }

                customer.setState(app.state.start);
                return cart.addItem(customer.getItemToAdd(), Integer.parseInt(count));
            case start:
                IStorageItem foundItem;
                var text = message.getText().split(" ");
                if (text[0].matches("\\d+")) {
                    var id = Integer.parseInt(text[0]);
                    foundItem = storage.getItemById(id);
                } else
                    foundItem = storage.getItemByName(text[0]);
                return foundItem == null
                        ? "\u2753 Товар не найден!"
                        : String.format("\uD83D\uDD0E Найденный товар:\n%s", foundItem);
            default:
                return "\u274C Что-то пошло не так!\n" +
                        "Введите /help для просмотра списка всех команд.";
        }
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