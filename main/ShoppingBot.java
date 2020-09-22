package main;

import botcommands.*;
import storages.*;
import java.util.ArrayList;

public class ShoppingBot {
    public final ArrayList<User> users;
    public IStorage storage;

    public ShoppingBot() {
        users = new ArrayList<>();
        storage = new Storage();
    }

    public void receiveMessage(User sender, String text) {
        if (text.equals(""))
            return;

        var args = text.split(" ");
        var defaultResponse = "Неизвестная команда! Введите /help для просмотра списка всех команд.";

        switch (args[0]) {
            case "/help" -> new HelpCommand(this).execute(sender, args);
            case "/search" -> new SearchCommand(this).execute(sender, args);
            case "/add" -> new AddToCartCommand(this).execute(sender, args);
            case "/cart" -> new ShowCartCommand(this).execute(sender, args);
            case "/stock" -> new ShowStockCommand(this).execute(sender, args);
            case "/clear" -> new ClearCartCommand().execute(sender, args);
            case "/exit" -> new ExitCommand().execute(sender, args);
            default -> sender.receiveResponse(defaultResponse);
        }
    }

    public static void main(String [] args) {
        var bot = new ShoppingBot();
        bot.users.add(new User(bot));
        for (var user : bot.users)
            user.waitForInput();
    }
}