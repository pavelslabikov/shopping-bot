package botcommands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class ShoppingCommand extends BotCommand {
    public static final Logger logger = LoggerFactory.getLogger(ShoppingCommand.class);

    public ShoppingCommand(String command, String description) {
        super(command, description);
    }

    void execute(AbsSender sender, Chat chat, String text) {
        var message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText(text);
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            logger.error("Caught exception with following stacktrace:", e);
        }
    }
}
