package storages;

import java.util.ArrayList;

public interface IStorage {
    ArrayList<String> getAllItems();
    IStorageItem getItemById(int id);
    IStorageItem getItemByName(String name);
    int getItemAmount(int id);
}