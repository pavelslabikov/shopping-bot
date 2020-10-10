package botcommands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;


public class HelpCommand extends ShoppingCommand {
    public HelpCommand() {
        super("/help", "help command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var text = "Список доступных команд:\n" +
                "/help - помощь по командам\n/cart - отобразить содержимое корзины\n" +
                "/clear - очистить содержимое корзины\n/search <ID> - поиск товара с идентификатором <ID>\n" +
                "/add <ID> <amount> - добавить <amount> товаров с идентификатором <ID> в корзину\n" +
                "/stock - отобразить все товары на складе";

        execute(absSender, chat, text);
    }
}