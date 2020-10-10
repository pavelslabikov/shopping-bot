package app;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {
    public static void main(String [] args) {
        ApiContextInitializer.init();
        var bot = new ShoppingBot();
        bot.registerCommands();
        try {
            var telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
