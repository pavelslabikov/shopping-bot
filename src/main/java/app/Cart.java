package app;

import storages.IStorageItem;

import java.util.HashMap;

public class Cart {
    private final HashMap<IStorageItem, Integer> content;

    public Cart() {
        content = new HashMap<>();
    }

    public String addItem(IStorageItem item, int amountToAdd, int amountInStock) {
        if (content.containsKey(item))
            return "\u274C Данный товар уже находится в корзине!";

        if (amountToAdd > amountInStock)
            return "Такого количества товара нет на складе!";

        content.put(item, amountToAdd);
        return "Товар успешно добавлен в корзину!";
    }

    public String removeItems() {
        content.clear();
        return "Корзина успешно очищена!";
    }

    public String getItems() {
        if (content.isEmpty())
            return "\u274CВаша корзина в данный момент пуста!";

        var allItems = new StringBuilder("\uD83D\uDED2 Список товаров в корзине:\n");
        for (var item : content.keySet())
            allItems.append(String.format("(%s) — %s шт.\n", item, content.get(item)));
        return allItems.toString();
    }
}
