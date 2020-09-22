package botcommands;

import main.ShoppingBot;
import main.User;

public class AddToCartCommand extends BotCommand {
    public AddToCartCommand(ShoppingBot botContext) {
        super(botContext);
    }

    @Override
    public void execute(User sender, String [] args) {
        if (args.length != 3) {
            sender.receiveResponse("Неверное количество аргументов для команды!\nИспользуйте: /add <ID> <amount>");
            return;
        }

        var id = Integer.parseInt(args[1]);
        var itemToAdd = bot.storage.getItem(id);
        if (itemToAdd == null) {
            sender.receiveResponse("Введённый идентефикатор товара не был найден на складе!");
            return;
        }

        if (sender.cart.containsKey(itemToAdd)) {
            sender.receiveResponse("Данный товар уже находится в корзине!");
            return;
        }

        try {
            Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e) {
            sender.receiveResponse("Некорректный формат ввода!");
            return;
        }

        var amountToAdd = Integer.parseInt(args[2]);
        if (amountToAdd > bot.storage.getItemAmount(id) || amountToAdd <= 0) {
            sender.receiveResponse("Такого количества товара нет в наличии / Некорректное число.");
            return;
        }

        sender.cart.put(itemToAdd, amountToAdd);
        sender.receiveResponse("Товар успешно добавлен в корзину!");
    }
}