package storages.google;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storages.IStorageItem;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class StorageItem implements IStorageItem {
    private final String name;
    private final int id;
    private final int price;
    private final int amount;
    private final String measure;
    private final String photoURL;

    private static final Logger logger = LoggerFactory.getLogger(StorageItem.class);

    public StorageItem(String name, int id, int price, int amount, String measure, String photoURL) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.amount = amount;
        this.measure = measure;
        this.photoURL = photoURL;
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

    public File getImage() {
        if (photoURL.equals("")) {
            logger.info("No image URL was found for item: {}", id);
            return null;
        }

        var imageExtension = FilenameUtils.getExtension(photoURL);
        var imageFullPath = String.format("%s%d - %s.%s", GoogleStorage.CACHE_PATH, id, name, imageExtension);
        var imageFile = new File(imageFullPath);
        try {
            if (!imageFile.createNewFile())
                return imageFile;
            var url = new URL(photoURL);
            var buffer = ImageIO.read(url);
            ImageIO.write(buffer, imageExtension, imageFile);
            return imageFile;
        } catch (IOException e) {
            logger.error("Unable to get image of item: {}, with URL: {}", id, photoURL, e);
            return null;
        }
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