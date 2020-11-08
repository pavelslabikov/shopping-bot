package storages;

import java.io.File;

public interface IStorageItem {
    int hashCode();
    int getId();
    int getAmount();
    File getImage();
    String toString();
}
