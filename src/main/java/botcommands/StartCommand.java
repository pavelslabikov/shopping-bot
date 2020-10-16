package botcommands;

import app.Customer;
import java.util.HashMap;


public class StartCommand implements IBotCommand {
    private final HashMap<Integer, Customer> customers;

    public StartCommand(HashMap<Integer, Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String getCommandIdentifier() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "null";
    }

    @Override
    public String execute(Integer userId, String[] args) {
        if (customers.containsKey(userId)) {
            return "\u274C Вы уже начали работу с ботом!";
        }

        else {
            customers.put(userId, new Customer(userId));
        }

        return "Вас приветствует Shopping-Bot v0.3!\n" +
                "Данный бот позволяет совершать покупки прямо со склада производителя.\n" +
                "Для просмотра полного функционала введите команду: /help";
    }
}