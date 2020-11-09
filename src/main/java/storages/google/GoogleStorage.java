package storages.google;

import com.google.api.services.sheets.v4.Sheets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storages.IStorage;
import storages.IStorageItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GoogleStorage implements IStorage {
    private final Sheets sheets;
    private final String sheetId;
    private final Character defaultWidth = 'G';

    private static final Logger logger = LoggerFactory.getLogger(GoogleStorage.class);
    static final String CACHE_PATH = "src/main/java/storages/google/cache/";

    public GoogleStorage(String id) {
        this.sheetId = id;
        this.sheets = SheetFactory.getSheetInstance();
        createCacheDir();
    }

    @Override
    public ArrayList<String> getAllItems() {
        var currentRange = String.format("A2:%c", defaultWidth);
        var grid = getValues(currentRange);
        var itemsList = new ArrayList<String>();
        if (grid != null) {
            for (var row : grid)
                itemsList.add(makeStorageItem(row).toString());
        }

        return itemsList;
    }

    @Override
    public IStorageItem getItemById(int id) {
        var currentRange = String.format("A%d:%c%d", id + 1, defaultWidth, id + 1);
        var values = getValues(currentRange);
        if (values != null)
            return makeStorageItem(values.get(0));

        return null;
    }

    @Override
    public IStorageItem getItemByName(String name) {
        var currentRange = String.format("A2:%c", defaultWidth);
        var values = getValues(currentRange);
        if (values != null) {
            for (var row : values)
                if (row.get(1).toString().equalsIgnoreCase(name))
                    return makeStorageItem(row);

        }

        return null;
    }

    private void createCacheDir() {
        var path = Paths.get(CACHE_PATH);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            logger.error("Cannot create directories: {}", CACHE_PATH, e);
        }
    }

    private StorageItem makeStorageItem(List<Object> row) {
        var imageURL = row.size() < 6 ? "" : row.get(5).toString();
        var modTime = row.size() < 7 ? "" : row.get(6).toString();
        return new StorageItem(row.get(1).toString(),
                Integer.parseInt(row.get(0).toString()),
                Integer.parseInt(row.get(4).toString()),
                Integer.parseInt(row.get(3).toString()),
                row.get(2).toString(),
                imageURL, modTime);
    }

    private List<List<Object>> getValues(String range) {
        try {
            var response = sheets.spreadsheets().values()
                    .get(sheetId, range)
                    .execute();
            if (response == null)
                logger.error("null response received from sheet: {}, of range: {}", sheetId, range);
            return response == null ? null : response.getValues();

        } catch (IOException e) {
            logger.error("Cannot get range: {}, in sheet: {} ", range, sheetId, e);
            return null;
        }
    }
}