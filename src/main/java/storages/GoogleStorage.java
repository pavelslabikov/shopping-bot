package storages;

import app.Main;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleStorage implements IStorage {
    private final Sheets sheets;
    private final String id;

    private static final Logger logger = LoggerFactory.getLogger(GoogleStorage.class);
    private static final String appName = "Shopping Bot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_PATH = "/credentials.json";

    public GoogleStorage(String id, Sheets sheets) {
        this.id = id;
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

    public void main() throws IOException {
        ValueRange response = sheets.spreadsheets().values()
                .get(id, "A1:E1")
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (var row : values)
                System.out.println(row.get(0));
        }
    }

    @Override
    public ArrayList<String> getAllItems() {
        return null;
    }

    @Override
    public IStorageItem getItemById(int id) {
        return null;
    }

    @Override
    public IStorageItem getItemByName(String name) {
        return null;
    }

    @Override
    public int getItemAmount(int itemId) {
        try {
            var response = sheets.spreadsheets().values()
                    .get(id, "")
                    .execute();
            var values = response.getValues();

        } catch (IOException e) {
            logger.error("Unable to send request to google sheet", e);
            return -1;
        }
        return 0;
    }
}