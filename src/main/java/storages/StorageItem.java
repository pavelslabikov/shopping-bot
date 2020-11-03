package storages;

import java.io.Serializable;

public class StorageItem implements IStorageItem, Serializable {
    private final String name;
    private final int id;
    private final int price;
    private final int amount;
    private final String measure;

    public StorageItem(String name, int id, int price, int amount, String measure){
        this.name = name;
        this.id = id;
        this.price = price;
        this.amount = amount;
        this.measure = measure;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StorageItem that = (StorageItem) o;
        return id == that.id &&
                price == that.price &&
                name.equalsIgnoreCase(that.name);
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Имя: %s, Количество на складе: %d %s, Цена: %d руб/%s", id, name, amount, measure, price, measure);
    }
}