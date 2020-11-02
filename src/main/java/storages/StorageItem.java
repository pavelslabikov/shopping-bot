package storages;

import java.io.Serializable;
import java.util.Objects;

public class StorageItem implements IStorageItem, Serializable {
    private final String name;
    private final int id;
    private final int price;
    private final int amount;

    public StorageItem(String name, int id, int price, int amount){
        this.name = name;
        this.id = id;
        this.price = price;
        this.amount = amount;
    }

    public String getName() { return name; }

    public String getLoverName() { return name.toLowerCase(); }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return amount;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageItem that = (StorageItem) o;
        return id == that.id &&
                price == that.price &&
                Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Имя: %s, Количество на складе: %d, Цена: %d", id, name, amount, price);
    }
}