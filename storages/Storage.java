package storages;

import java.util.ArrayList;
import java.util.List;

public class Storage implements IStorage{
    public ArrayList<StorageItem> items;

    public Storage(){
        var items = new ArrayList<StorageItem>();
        for (var i = 0; i < 5; i++)
            items.add(new StorageItem("First Item", i, i + 100, 5));
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

    public StorageItem getItem(int id){
        for (var e : items){
            if (e.getId() == id)
                return e;
        }

        return null;
    }

    @Override
    public int getItemAmount(int id) {
        return getItem(id).getCount();
    }
}
