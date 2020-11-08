package app;

import botcommands.CommandAdapter;
import models.BotMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
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

    private void sendReplyToUser(Long chatId, BotMessage botMessage) {
       if (botMessage.hasPhoto())
        {
            var reply = new SendPhoto();
            reply.setChatId(chatId);
            reply.setCaption(botMessage.getText());
            reply.setPhoto(botMessage.getPhoto());
            processReply(reply);
        } else {
            var reply = new SendMessage();
            reply.setChatId(chatId);
            reply.setText(botMessage.getText());
            processReply(reply);
        }
    }

    private void processReply(PartialBotApiMethod<Message> messageToSend) {
        if (messageToSend instanceof SendPhoto)
        {
            try {
                execute((SendPhoto) messageToSend);
            } catch (TelegramApiException e) {
                logger.error("Cannot send photo message to user: {}", messageToSend, e);
            }
        } else {
            try {
                execute((SendMessage) messageToSend);
            } catch (TelegramApiException e) {
                logger.error("Cannot send text reply to user: {}", messageToSend, e);
            }
        }
    }
}