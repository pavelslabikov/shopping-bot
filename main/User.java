package main;

import storages.IStorageItem;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class User {
    public final Map<IStorageItem, Integer> cart;
    public final int id;

    public User() {
        cart = new HashMap<>();
        id = 1;
        Main.printMessage("Вас приветствует ShoppingBot v0.1!\n" +
                "Данный бот позволяет совершать покупки прямо со склада производителя.\n" +
                "Для просмотра полного функционала введите команду: /help\n");
        var previousCart = importLogs();
        if (!previousCart.equals("")) {
            Main.printMessage("В прошлый раз вы забыли купить:\n");
            Main.printMessage(previousCart);
        }

    }

    public void exportCartContent() {
        try {
            var writer = new FileWriter(String.format("main/logs/log%s.txt", id), false);
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
            var writer = new FileReader(String.format("main/logs/log%s.txt", id));
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
}
