package app;

import botcommands.*;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import storages.*;

import java.util.HashMap;

import org.telegram.telegrambots.meta.api.objects.Update;

public class ShoppingBot extends TelegramLongPollingCommandBot{
    public final HashMap<Integer, Cart> carts;

    private final IStorage storage;

    public ShoppingBot() {
        carts = new HashMap<>();
        storage = new Storage();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        var message = new SendMessage();
        message.setText("Необрабатываеммая комманда " + update.getMessage().getText());
        message.setChatId(update.getMessage().getChatId());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void registerCommands() {
        register(new HelpCommand("/help", "help command"));
        register(new ShowCartCommand("/cart", "shows your cart", carts));
        register(new ClearCartCommand("/clear", "clear your cart", carts));
        register(new SearchCommand("/search", "search item in stocks", storage));
        register(new AddToCartCommand("/add", "adds item to cart", storage, carts));
        register(new ShowStockCommand("/stock", "show all stock", storage));
    }

    @Override
    public String getBotToken() {
        return "1285957379:AAFT9cJWTyzxn5C2qZ4oU55U9SbKr6j0cbE";
    }

    @Override
    public String getBotUsername() {
        return "javaShoppingBot";
    }
}