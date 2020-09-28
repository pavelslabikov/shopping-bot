package main;

import botcommands.*;
import storages.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingBot {
    public final ArrayList<User> users;

    private final IStorage storage;
    private final Map<String, BotCommand> commands;

    public ShoppingBot() {
        users = new ArrayList<>();
        storage = new Storage();
        commands = new HashMap<>();
    }

    public void receiveMessage(User user, String text) {
        if (text.equals(""))
            return;

        var args = text.split(" ");
        var defaultResponse = "Неизвестная команда! Введите /help для просмотра списка всех команд.";
        if (commands.containsKey(args[0]))
            commands.get(args[0]).execute(user, args);
        else
            Main.printMessage(defaultResponse);
    }

    public void registerCommands() {
        commands.put("/help", new HelpCommand());
        commands.put("/cart", new ShowCartCommand());
        commands.put("/clear", new ClearCartCommand());
        commands.put("/exit", new ExitCommand());
        commands.put("/search", new SearchCommand(storage));
        commands.put("/add", new AddToCartCommand(storage));
        commands.put("/stock", new ShowStockCommand(storage));
    }
}