package botcommands;

import models.BotMessage;
import models.Customer;
import models.Keyboard;
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
        return "Start working with bot";
    }

    @Override
    public BotMessage execute(Integer userId, String[] args) {
        var resultMessage = new BotMessage();
        if (customers.containsKey(userId)) {
            logger.info("Customer {} tried to /start already working bot", userId);
            resultMessage.setText("\u274C Вы уже начали работу с ботом!");
        } else {
            logger.info("User {} has become a new customer", userId);
            customers.put(userId, new Customer(userId));
            resultMessage.setKeyboard(new Keyboard());
            resultMessage.setText("Вас приветствует Shopping-Bot v0.3!\n" +
                    "Данный бот позволяет совершать покупки прямо со склада производителя.\n" +
                    "Для просмотра списка команд введите команду: /help\n" +
                    "Для поиска нужного товара, просто отправьте боту его ID или имя");
        }

        return resultMessage;
    }
}