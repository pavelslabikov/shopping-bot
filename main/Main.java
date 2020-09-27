package main;

import java.util.Scanner;

public class Main {
    public static void main(String [] args) {
        var bot = new ShoppingBot();
        bot.registerCommands();
        bot.users.add(new User(1));
        var input = new Scanner(System.in);
        while (true) {
            for (var user : bot.users) {
                var userMessage = input.nextLine();
                bot.receiveMessage(user, userMessage);
            }
        }
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }
}
