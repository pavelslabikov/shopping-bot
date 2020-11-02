package models;

public class BotMessage {
    private String text;
    private Keyboard keyboard;

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
}
