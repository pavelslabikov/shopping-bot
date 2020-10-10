package app;

import storages.IStorageItem;

import java.io.*;
import java.util.HashMap;

public class Cart {
    private final HashMap<IStorageItem, Integer> content;

    public Cart() {
        content = new HashMap<>();
    }

    public String addItem(IStorageItem item, int amountToAdd, int amountInStock) {
        if (content.containsKey(item)) {
            return "\u274C Данный товар уже находится в корзине!";
        }

        if (amountToAdd > amountInStock) {
            return "Такого количества товара нет на складе!";
        }

        content.put(item, amountToAdd);
        return "Товар успешно добавлен в корзину!";
    }

    public String removeItems() {
        content.clear();
        return "Корзина успешно очищена!";
    }

    public String getItems() {
        if (content.isEmpty())
            return "Ваша корзина в данный момент пуста!";

        var allItems = new StringBuilder("\uD83D\uDED2 Список товаров в корзине:\n");
        for (var item : content.keySet())
            allItems.append(String.format("(%s) — %s шт.\n", item, content.get(item)));
        return allItems.toString();
    }

    public void exportContent(String filename) {
        try {
            var file = new FileOutputStream(filename, false);
            var objectStream = new ObjectOutputStream(file);
            objectStream.writeObject(content.keySet().toArray());
            objectStream.flush();
            objectStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String importContent(String filename) {
        if (!new File(filename).exists())
            return "";

        var response = new StringBuilder();
        try {
            var objectStream = new ObjectInputStream(new FileInputStream(filename));
            var items = (Object[]) objectStream.readObject();
            if (items.length != 0) {
                response.append("В прошлый раз вы забыли оформить заказ на следующие товары:\n");
                for (var item : items) {
                    response.append(item.toString());
                    response.append("\n");
                }
            }

            objectStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
