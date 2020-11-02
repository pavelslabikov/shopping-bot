package botcommands;

import models.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CommandAdapter extends BotCommand {
    private static final Logger logger = LoggerFactory.getLogger(CommandAdapter.class);

    private final IBotCommand command;

    public CommandAdapter(IBotCommand command) {
        super(command.getCommandIdentifier(), command.getDescription());
        this.command = command;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] args) {
        var response = command.execute(user.getId(), args);
        var message = new SendMessage();
        message.setChatId(chat.getId());
        if (response.getKeyboard() != null) {
            message.setReplyMarkup(response.getKeyboard().getKeyboard());
            message.enableMarkdown(true);
        }

        message.setText(response.getText());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            logger.error("Cannot send reply for command {}: {}", getCommandIdentifier(), message, e);
        }
    }
}
