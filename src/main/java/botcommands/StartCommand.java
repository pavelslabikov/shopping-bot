package botcommands;

import app.Customer;
import app.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;


public class StartCommand extends ShoppingCommand {
    private final HashMap<Integer, Customer> customers;
    private final Keyboard keyboard;

    public StartCommand(HashMap<Integer, Customer> customers) {
        super("/start", "start working with bot");
        this.customers = customers;
        this.keyboard = new Keyboard();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var text = "Вас приветствует Shopping-Bot v0.3!\n" +
                "Данный бот позволяет совершать покупки прямо со склада производителя.\n" +
                "Для просмотра полного функционала введите команду: /help";

        if (customers.containsKey(user.getId())) {
            text = "\u274C Вы уже начали работу с ботом!";
            logger.info("Customer {} tried to use /start again.", user.getUserName());
        }

        else {
            customers.put(user.getId(), new Customer(user.getId(), user.getUserName()));
            logger.info("User {} started working with bot.", user.getUserName());
        }

        var message = new SendMessage();
        message.enableMarkdown(true);
        message.setReplyMarkup(keyboard.getKeyboard());
        message.setChatId(chat.getId());
        message.setText(text);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            logger.error("Caught exception with following stacktrace:", e);
        }
    }
}