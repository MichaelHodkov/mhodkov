package ru.job4j.exchange;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Claim {
    private static int count;
    private int id;
    private String book;
    private int type;
    private int action;
    private float price;
    private int volume;

    public Claim(String book, int type, int action, float price, int volume) {
        this.book = book;
        this.type = type;
        this.action = action;
        this.price = price;
        this.volume = volume;
        this.id = count;
        count++;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public String getBook() {
        return this.book;
    }

    public int getType() {
        return this.type;
    }

    public int getAction() {
        return this.action;
    }

    public float getPrice() {
        return this.price;
    }

    public int getVolume() {
        return this.volume;
    }

    @Override
    public String toString() {
        String action;
        if (this.action == Action.ASK) {
            action = "продажа";
        } else {
            action = "покупка";
        }
        return String.format("#%d %s %s по цене: %.2f - %d шт.",
                this.id, this.book, action, this.price, this.volume);
    }

    class Type {
        public static final int ADD = 1;
        public static final int DEL = 0;
    }

    class Action {
        public static final int BID = 0;
        public static final int ASK = 1;
    }
}
