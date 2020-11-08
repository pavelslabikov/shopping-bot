package models;

import java.io.File;

public class BotMessage {
    private String text;
    private Keyboard keyboard;
    private File photo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text != null)
            this.text = text;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        if (keyboard != null)
            this.keyboard = keyboard;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public boolean hasPhoto() {
        return photo != null;
    }
}
