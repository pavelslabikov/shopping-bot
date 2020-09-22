package main;

import storages.IStorageItem;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class User {
    private final ShoppingBot bot;
    public final Map<IStorageItem, Integer> cart;

    public User(ShoppingBot bot) {
        this.bot = bot;
        cart = new HashMap<>();
        receiveResponse("Вас приветствует ShoppingBot v0.1!\n" +
                "Данный бот позволяет совершать покупки прямо со склада производителя.\n" +
                "Для просмотра полного функционала введите команду: /help\n");
        var previousCart = importLogs();
        if (!previousCart.equals("")) {
            receiveResponse("В прошлый раз вы забыли купить:\n");
            receiveResponse(previousCart);
        }

    }

    public void waitForInput() {
        var inputScanner = new Scanner(System.in);
        while (true) {
            var userMessage = inputScanner.nextLine();
            bot.receiveMessage(this, userMessage);
        }
    }

    public void exportCartContent() {
        try {
            var writer = new FileWriter("log.txt", false);
            for (var item : cart.keySet())
                writer.write(String.format("(%s) — %s шт.\n", item, cart.get(item)));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String importLogs() {
        try {
            var text = new char[256];
            var result = new StringBuilder();
            var writer = new FileReader("log.txt");
            while (writer.read(text) > 0)
            {
                result.append(text);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void receiveResponse(String text) {
        System.out.println(text);
    }
}
