package storages;

import com.google.api.services.sheets.v4.Sheets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleStorage implements IStorage {
    private final Sheets sheets;
    private final String sheetId;
    private final Character defaultWidth = 'E';

    private static final Logger logger = LoggerFactory.getLogger(GoogleStorage.class);

    public GoogleStorage(String id) {
        this.sheetId = id;
        this.sheets = SheetFactory.getSheetInstance();
    }

    @Override
    public ArrayList<String> getAllItems() {
        var currentRange = String.format("A2:%c4", defaultWidth);
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
        var currentRange = String.format("A2:%c4", defaultWidth);
        var values = getValues(currentRange);
        if (values != null) {
            for (var row : values)
                if (row.get(1).toString().equalsIgnoreCase(name))
                    return makeStorageItem(row);

        }

        return null;
    }

    private StorageItem makeStorageItem(List<Object> row){
        return new StorageItem(row.get(1).toString(),
                Integer.parseInt(row.get(0).toString()),
                Integer.parseInt(row.get(4).toString()),
                Integer.parseInt(row.get(3).toString()),
                row.get(2).toString());
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