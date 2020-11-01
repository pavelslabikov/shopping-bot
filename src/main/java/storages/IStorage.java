package storages;

import java.io.IOException;
import java.util.ArrayList;

public interface IStorage {
    ArrayList<String> getAllItems();
    IStorageItem getItemById(int id);
    IStorageItem getItemByName(String name);
}