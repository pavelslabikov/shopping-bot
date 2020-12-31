package storages.test;

import storages.IStorageItem;

import java.io.File;
import java.util.Objects;

public class TestStorageItem implements IStorageItem {
    private final int id;
    private final int amount;

    public TestStorageItem(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public File getImage() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TestStorageItem that = (TestStorageItem) o;
        return id == that.id &&
                amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
