package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String [] args) {
        logger.info("Starting application...");
        ApiContextInitializer.init();
        var bot = new ShoppingBot();
        bot.registerCommands();
        try {
            var telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            logger.error("Caught exception with following stacktrace:", e);
        }
    }
}
