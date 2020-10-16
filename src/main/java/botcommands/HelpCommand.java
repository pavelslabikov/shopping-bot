package botcommands;

public class HelpCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "null";
    }

    @Override
    public String execute(Integer userId, String[] args) {
        return "Список доступных команд:\n" +
                "/help - помощь по командам\n/cart - отобразить содержимое корзины\n" +
                "/clear - очистить содержимое корзины\n/search <ID> - поиск товара с идентификатором <ID>\n" +
                "/add <ID> <amount> - добавить <amount> товаров с идентификатором <ID> в корзину\n" +
                "/stock - отобразить все товары на складе";
    }
}