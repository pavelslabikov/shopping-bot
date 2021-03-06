package botcommands;

import models.BotMessage;

public class HelpCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "Shows command help";
    }

    @Override
    public BotMessage execute(Integer userId, String[] args) {
        var resultMessage = new BotMessage();
        resultMessage.setText("Список доступных команд:\n" +
                "/help - помощь по командам\n" + "/start - начать работу с ботом\n" +
                "/cart - отобразить содержимое корзины\n" +
                "/clear - очистить содержимое корзины\n" +
                "/add - начать добавление товара в корзину\n" +
                "/stock - отобразить все товары на складе");
        return resultMessage;
    }
}