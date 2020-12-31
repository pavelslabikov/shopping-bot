package models;

import storages.IStorageItem;

import java.util.HashMap;
import java.util.Set;

public class Cart {
    private final HashMap<IStorageItem, Integer> content;

    public Cart() {
        content = new HashMap<>();
    }

    public String addItem(IStorageItem item, int amountToAdd) {
        var amountInStock = item.getAmount();
        if (content.containsKey(item))
            return "\u274C Данный товар уже находится в корзине!";

        if (amountToAdd > amountInStock)
            return "Такого количества товара нет на складе!";

        content.put(item, amountToAdd);
        return "Товар успешно добавлен в корзину!";
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public String removeItems() {
        content.clear();
        return "Корзина успешно очищена!";
    }

    public Set<IStorageItem> getItems() {
        return content.keySet();
    }
}
