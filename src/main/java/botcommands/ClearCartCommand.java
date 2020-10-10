package botcommands;


import app.Customer;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.HashMap;

public class ClearCartCommand extends ShoppingCommand {
    private final HashMap<Integer, Customer> customers;

    public ClearCartCommand(HashMap<Integer, Customer> customers) {
        super("/clear", "clears cart");
        this.customers = customers;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (!customers.containsKey(user.getId())) {
            execute(absSender, chat, "\u274C Прежде чем вводить данную команду, начните работу с ботом!");
            return;
        }

        var currentCustomer = customers.get(user.getId());
        var response = currentCustomer.getCart().removeItems();
        execute(absSender, chat, response);
    }
}

