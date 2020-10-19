package botcommands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IBotCommand {
    Logger logger = LoggerFactory.getLogger(IBotCommand.class);

    String getCommandIdentifier();

    String getDescription();

    String execute(Integer userId, String[] args);
}
