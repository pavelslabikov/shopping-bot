package botcommands;

import app.Cart;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import storages.*;

import java.util.HashMap;

public class AddToCartCommand extends BotCommand {
    private final IStorage storage;
    private final HashMap<Integer, Cart> carts;

    public AddToCartCommand(String commandIdentifier, String description, IStorage storage, HashMap<Integer, Cart> carts) {
        super(commandIdentifier, description);
        this.storage = storage;
        this.carts = carts;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var cart = carts.get(user.getId());
        IStorageItem itemToAdd;
        var response = "";
        var message = new SendMessage();
        message.setChatId(chat.getId());
        if (strings.length != 2)
            response = "Неверное количество аргументов для команды!\nИспользуйте: /add <ID> <amount>";

        try {
            Integer.parseInt(strings[0]);
        } catch (NumberFormatException e) {
            response = "Некорректный формат ввода!";
        }

        if (response.equals("") && strings[0].matches("^\\d+")) {
            var id = Integer.parseInt(strings[0]);
            itemToAdd = storage.getItemById(id);
            if (itemToAdd == null) {
                response = "Введённый идентификатор товара не был найден на складе!";
                return;
            }

            response = cart.addItem(itemToAdd, Integer.parseInt(strings[1]), storage.getItemAmount(id));
            carts.put(user.getId(), cart);
        }

        else if (response.equals("")){
            itemToAdd = storage.getItemByName(strings[0]);
            if (itemToAdd == null) {
                response = "Введённое имя товара не было найдено на складе!";
            }

            var id = itemToAdd.getId();
            response = cart.addItem(itemToAdd, Integer.parseInt(strings[0]), storage.getItemAmount(id));
            carts.put(user.getId(), cart);
        }

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}