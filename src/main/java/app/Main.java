package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import storages.GoogleStorage;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String [] args) {
        logger.info("Initializing API context...");
        ApiContextInitializer.init();
        var storage = GoogleStorage.getStorageObject();
        var bot = new ShoppingBot(storage);
        var tgWrapper = new TelegramWrapper(bot);
        tgWrapper.registerCommands();

        try {
            var telegramBotsApi = new TelegramBotsApi();

            telegramBotsApi.registerBot(tgWrapper);
        } catch (TelegramApiRequestException e) {
            logger.error("Telegram API cannot register bot:", e);
        }
    }
}
