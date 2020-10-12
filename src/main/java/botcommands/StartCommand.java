package botcommands;

import app.Customer;
import app.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;


public class StartCommand extends ShoppingCommand {
    private final HashMap<Integer, Customer> customers;
    private final ReplyKeyboardMarkup keyboard;

    public StartCommand(HashMap<Integer, Customer> customers) {
        super("/start", "start working with bot");
        this.customers = customers;
        var keyboard = new Keyboard();
        this.keyboard = keyboard.getKeyboard();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var text = "Вас приветствует Shopping-Bot v0.3!\n" +
                "Данный бот позволяет совершать покупки прямо со склада производителя.\n" +
                "Для просмотра полного функционала введите команду: /help";

        if (customers.containsKey(user.getId())) {
            text = "\u274C Вы уже начали работу с ботом!";
            logger.info("User {} tried to use command without starting the bot.", user.getUserName());
        }

        else {
            customers.put(user.getId(), new Customer(user.getId(), user.getUserName()));
            logger.info("User {} started working with bot.", user.getUserName());
        }

        var message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText(text);
        message.enableMarkdown(true);
        message.setReplyMarkup(keyboard);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}