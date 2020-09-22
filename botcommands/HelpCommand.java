package botcommands;

import main.Main;
import main.User;


public class HelpCommand extends BotCommand {
    @Override
    public void execute(User sender, String [] args) {
        Main.printMessage("Список доступных команд:\n" +
                "/help - помощь по командам\n/cart - отобразить содержимое корзины\n" +
                "/clear - очистить содержимое корзины\n/search <ID> - поиск товара с идентификатором <ID>\n" +
                "/add <ID> <amount> - добавить <amount> товаров с идентификатором <ID> в корзину\n" +
                "/stock - отобразить все товары на складе\n/exit - завершить работу с ботом" );
    }
}
