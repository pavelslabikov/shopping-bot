package app;

import botcommands.CommandAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramWrapper extends TelegramLongPollingCommandBot {
    private static final Logger logger = LoggerFactory.getLogger(TelegramWrapper.class);

    private final ShoppingBot bot;

    public TelegramWrapper(ShoppingBot bot) {
        this.bot = bot;
    }

    public void registerCommands() {
        for (var command : bot.availableCommands)
            if (!register(new CommandAdapter(command)))
                logger.error("Cannot register command: {}", command.getCommandIdentifier());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (!update.hasMessage()) {
            logger.debug("An update without message received: {}", update);
            return;
        }

        var message = update.getMessage();
        var currentChatId = message.getChatId();
        var userId = message.getFrom().getId();
        var response = bot.answerUser(userId, message.getText());
        sendReplyToUser(currentChatId, response);
    }

    @Override
    public String getBotToken() {
        return System.getenv("TOKEN");
    }

    @Override
    public String getBotUsername() {
        return "javaShoppingBot";
    }

    private void sendReplyToUser(Long chatId, String text) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Cannot send reply to user: {}", message, e);
        }
    }
}
