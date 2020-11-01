package storages;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleStorage implements IStorage {
    private final Sheets sheets;
    private final String sheetId;
    private final Character defaultWidth = 'E';

    private static final Logger logger = LoggerFactory.getLogger(GoogleStorage.class);
    private static final String appName = "Shopping Bot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_PATH = "/credentials.json";

    public GoogleStorage(String id, Sheets sheets) {
        this.sheetId = id;
        this.sheets = sheets;
    }

    public static GoogleStorage getStorageObject() {
        var sheetId = "1qTfBjodUewHdDtNgB3KmVmUm6Xyler3f39mQ9EtfoMY";

        try {
            var transport = GoogleNetHttpTransport.newTrustedTransport();
            var in = GoogleStorage.class.getResourceAsStream(CREDENTIALS_PATH);
            var clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            var flow = new GoogleAuthorizationCodeFlow.Builder(
                    transport, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_PATH)))
                    .setAccessType("offline")
                    .build();

            var receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            var credentials = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
            var sheets = new Sheets.Builder(transport, JSON_FACTORY, credentials)
                    .setApplicationName(appName)
                    .build();
            return new GoogleStorage(sheetId, sheets);

        } catch (IOException e) {
            logger.error("Unable to load client secrets:");
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            logger.error("Cannot create new trusted http transport:");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> getAllItems() {
        var values = getValues("A2:E4");
        var itemsList = new ArrayList<String>();
        if (values != null){
            for (var row : values){
                itemsList.add(makeStorageItem(row).toString());
            }
        }
        return itemsList;
    }

    @Override
    public IStorageItem getItemById(int id) {
        var currentRange = String.format("A%d:%c%d", id + 1, defaultWidth, id + 1);
        var values = getValues(currentRange);
        if (values != null) {
            return makeStorageItem(values.get(0));
        }
        return null;
    }

    @Override
    public IStorageItem getItemByName(String name) {
        var values = getValues("A2:E4");
        if (values != null) {
            for (var row:values) {
                if (row.get(1).toString().toLowerCase().equals(name.toLowerCase())){
                    return makeStorageItem(row);
                }
            }
        }
        return null;
    }

    private StorageItem makeStorageItem(List<Object> row){
        return new StorageItem(row.get(1).toString(), Integer.parseInt(row.get(0).toString()), Integer.parseInt(row.get(4).toString()), Integer.parseInt(row.get(3).toString()));
    }

    private List<List<Object>> getValues(String range){
        ValueRange response = null;
        try {
            response = sheets.spreadsheets().values()
                    .get(sheetId, range)
                    .execute();
        } catch (IOException e) {
            logger.error("Can not connect to GoogleSheets");
        }
        if (response != null) {
            return response.getValues();
        }
        logger.error("null response in GoogleStorage.GetValues");
        return null;
    }
}