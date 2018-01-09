package ru.job4j.tracker;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class StartUI {

    /**
     * Константа меню для выхода из цикла.
     */
    private static final String EXIT = "6";

    /**
     * Получение данных от пользователя.
     */
    private final Input input;

    /**
     * Хранилище заявок.
     */
    private final Tracker tracker;

    /**
     * Конструтор инициализирующий поля.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        String key;
        do {
            menu.show();
            System.out.println();
            key = this.input.ask("Select menu: ");
            menu.select(Integer.parseInt(key));
        } while (!EXIT.equals(key));
    }

    /**
     * Запускт программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}