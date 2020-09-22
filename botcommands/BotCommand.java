package botcommands;

import main.ShoppingBot;
import main.User;

public abstract class BotCommand {
    void execute(User sender, String [] args) { }
}
