package botcommands;

import app.Customer;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.HashMap;


public class StartCommand extends ShoppingCommand {
    private final HashMap<Integer, Customer> customers;

    public StartCommand(HashMap<Integer, Customer> customers) {
        super("/start", "start working with bot");
        this.customers = customers;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var text = "Вас приветствует Shopping-Bot v0.3!\n" +
                "Данный бот позволяет совершать покупки прямо со склада производителя.\n" +
                "Для просмотра полного функционала введите команду: /help";

        if (customers.containsKey(user.getId()))
            text = "\u274C Вы уже начали работу с ботом!";
        else
            customers.put(user.getId(), new Customer(user.getId(), user.getUserName()));

        execute(absSender, chat, text);
    }
}