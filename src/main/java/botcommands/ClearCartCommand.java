package botcommands;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import app.Cart;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class ClearCartCommand extends BotCommand {

    private final HashMap<Integer, Cart> carts;
    public ClearCartCommand(String commandIdentifier, String description, HashMap<Integer, Cart> carts) {
        super(commandIdentifier, description);
        this.carts = carts;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var cart = carts.get(user.getId());
        cart.removeItems();
        var message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText("Корзина успешно очищена!");
        carts.put(user.getId(), cart);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

