package main;

import storages.IStorageItem;

import java.io.*;
import java.util.HashMap;

public class Cart {
    private final HashMap<IStorageItem, Integer> content;

    public Cart() {
        content = new HashMap<>();
    }

    public void addItem(IStorageItem item, int amountToAdd, int amountInStock) {
        if (content.containsKey(item)) {
            Main.printMessage("Данный товар уже находится в корзине!");
            return;
        }

        if (amountToAdd > amountInStock) {
            Main.printMessage("Такого количества товара нет на складе!");
            return;
        }

        content.put(item, amountToAdd);
        Main.printMessage("Товар успешно добавлен в корзину!");
    }

    public void removeItems() {
        content.clear();
    }

    public String getItems() {
        if (content.isEmpty())
            return "Ваша корзина в данный момент пуста!";

        var allItems = new StringBuilder("Список товаров в корзине:\n");
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

    public void importContent(String filename) {
        if (!new File(filename).exists())
            return;

        try {
            var objectStream = new ObjectInputStream(new FileInputStream(filename));
            var items = (Object[]) objectStream.readObject();
            if (items.length != 0) {
                Main.printMessage("В прошлый раз вы забыли оформить заказ на следующие товары:\n");
                for (var item : items)
                    Main.printMessage(item.toString());
            }

            objectStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
