package storages;

import java.util.ArrayList;

public class SimpleStorage implements IStorage{
    public ArrayList<StorageItem> items;

    public SimpleStorage(){
        var items = new ArrayList<StorageItem>();

        items.add(new StorageItem("First_Item", 1, 100, 5));
        items.add(new StorageItem("Second_Item", 2, 100, 5));
        items.add(new StorageItem("Item1", 3, 100, 5));
        items.add(new StorageItem("Trava", 228, 2500, 1000));

        this.items = items;
    }

    public void insertItem(StorageItem item){
        items.add(item);
    }

    public boolean checkItem(StorageItem item){
        return items.contains(item);
    }

    public ArrayList<String> getAllItems(){
        var result = new ArrayList<String>();
        for (var item : items)
            result.add(item.toString());

        return result;
    }

    public StorageItem getItemById(int id){
        for (var e : items){
            if (e.getId() == id)
                return e;
        }

        return null;
    }

    public StorageItem getItemByName(String name){
        name = name.toLowerCase();
        for (var e : items){
            if (e.getLoverName().equals(name))
                return e;
        }

        return null;
    }
}