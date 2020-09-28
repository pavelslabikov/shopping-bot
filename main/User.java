package main;

public class User {
    public final Cart cart;
    private final int id;

    public User(int id) {
        this.id = id;
        Main.printMessage("Вас приветствует ShoppingBot v0.2!\n" +
                "Данный бот позволяет совершать покупки прямо со склада производителя.\n" +
                "Для просмотра полного функционала введите команду: /help\n");
        cart = new Cart();
        cart.importContent(String.format("main/logs/user-log%d.txt", id));
    }

    public int getId() {
        return id;
    }
}
