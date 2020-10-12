package app;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class Keyboard {

    public ReplyKeyboardMarkup getKeyboard(){
        final var keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(false);
        keyboard.setResizeKeyboard(true);

        var keyboardRowList = new ArrayList<KeyboardRow>();

        var row1 = new KeyboardRow();
        var row2 = new KeyboardRow();
        var row3 = new KeyboardRow();
        row1.add(new KeyboardButton("/help"));
        row2.add(new KeyboardButton("/stock"));
        row3.add(new KeyboardButton("/cart"));
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        keyboardRowList.add(row3);
        keyboard.setKeyboard(keyboardRowList);
        return keyboard;
    }
}
