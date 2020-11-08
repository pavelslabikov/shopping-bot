package storages.google;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class SheetFactory {
    private static final Logger logger = LoggerFactory.getLogger(SheetFactory.class);
    private static final String appName = "Shopping Bot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_PATH = "/credentials.json";

    public static Sheets getSheetInstance() {
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
            return new Sheets.Builder(transport, JSON_FACTORY, credentials)
                    .setApplicationName(appName)
                    .build();

        } catch (IOException e) {
            logger.error("Unable to load client secrets:");
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            logger.error("Cannot create new trusted http transport:");
            throw new RuntimeException(e);
        }
    }
}
