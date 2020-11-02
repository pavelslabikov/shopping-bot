package botcommands;

import models.BotMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IBotCommand {
    Logger logger = LoggerFactory.getLogger(IBotCommand.class);

    String getCommandIdentifier();

    String getDescription();

    BotMessage execute(Integer userId, String[] args);
}
