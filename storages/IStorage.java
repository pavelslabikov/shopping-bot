package storages;

import java.util.ArrayList;

public interface IStorage {
    ArrayList<String> getAllItems();
    IStorageItem getItem(int id);
    int getItemAmount(int id);
}