package models;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class Keyboard {
    private final ReplyKeyboardMarkup mainKeyboard;

    public Keyboard() {
        mainKeyboard = new ReplyKeyboardMarkup();
        adjustKeyboard();
    }

    private void adjustKeyboard() {
        mainKeyboard.setOneTimeKeyboard(false);
        mainKeyboard.setResizeKeyboard(true);

        var keyboardRows = new ArrayList<KeyboardRow>();

        var row1 = new KeyboardRow();
        var row2 = new KeyboardRow();
        var row3 = new KeyboardRow();

        row1.add(new KeyboardButton("/help"));
        row2.add(new KeyboardButton("/stock"));
        row3.add(new KeyboardButton("/cart"));

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        mainKeyboard.setKeyboard(keyboardRows);
    }

    public ReplyKeyboardMarkup getKeyboard(){
        return mainKeyboard;
    }
}
