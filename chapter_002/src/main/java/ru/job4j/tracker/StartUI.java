package ru.job4j.tracker;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class StartUI {

    /**
     * Константа меню для добавления новой заявки.
     */
    private static final String ADD = "0";

    /**
     * Константа меню для отображения всех заявок.
     */
    private static final String SHOW = "1";

    /**
     * Константа меню для редактирования заявки.
     */
    private static final String EDIT = "2";

    /**
     * Константа меню для удаления заявки.
     */
    private static final String DELETE = "3";

    /**
     * Константа меню для поиска заявки по Id.
     */
    private static final String FINDID = "4";

    /**
     * Константа меню для поиска заявокк по имени.
     */
    private static final String FINDNAME = "5";

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
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Введите пункт меню : ");
            if (ADD.equals(answer)) {
                //Добавление заявки вынесено в отдельный метод.
                this.createItem();
                //Добавить остальные действия системы по меню.
            } else if (SHOW.equals(answer)) {
                this.showItem();
            } else if (EDIT.equals(answer)) {
                this.editItem();
            } else if (DELETE.equals(answer)) {
                this.deleteItem();
            } else if (FINDID.equals(answer)) {
                this.findId();
            } else if (FINDNAME.equals(answer)) {
                this.findName();
            } else if (EXIT.equals(answer)) {
                exit = true;
            }
        }
    }

    /**
     * Метод реализует добавленяи новый заявки в хранилище.
     */
    private void createItem() {
        System.out.println("------------ Добавление новой языки --------------");
        String name = this.input.ask("Введите имя заявки :");
        String desc = this.input.ask("Введите описание заявки :");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("------------ Новая заявка с getId : " + item.getId() + " -----------");
    }

    /**
     * Метод вывода всех заявок.
     */
    private void showItem() {
        System.out.println("------------ Все заявки --------------");
        boolean flag = true;
        for (Item iter: this.tracker.findAll()) {
            System.out.println("id: " + iter.getId());
            System.out.println("Имя заявки : " + iter.getName());
            System.out.println("Описание заявки :" + iter.getDesc());
            System.out.println("--------------------------------------");
            flag = false;
        }
        if (flag) {
            System.out.println("--------------------------------------");
        }
    }

    /**
     * Метод редактирования заявки.
     */
    private void editItem() {
        System.out.println("------------ Редактировать заявку по id --------------");
        String answer = this.input.ask("Введите id : ");
        String name = this.input.ask("Введите имя заявки :");
        String desc = this.input.ask("Введите описание заявки :");
        Item item = new Item(name, desc);
        this.tracker.replace(answer, item);
        System.out.println("------------------------------------------------------");
    }

    /**
     * Метод удаления заявок.
     */
    private void deleteItem() {
        System.out.println("------------ Удалить заявку по id --------------");
        String answer = this.input.ask("Введите id : ");
        this.tracker.delete(answer);
        System.out.println("------------------------------------------------");
    }

    /**
     * Метод поиска заявки по Id.
     */
    private void findId() {
        System.out.println("------------ Поиск заявки по id --------------");
        String answer = this.input.ask("Введите id : ");
        Item item = this.tracker.findById(answer);
        if (item != null) {
            System.out.println("id: " + item.getId());
            System.out.println("Имя заявки : " + item.getName());
            System.out.println("Описание заявки :" + item.getDesc());
        }
        System.out.println("----------------------------------------------");
    }

    /**
     * Метод поиска заявок по имени.
     */
    private void findName() {
        System.out.println("------------ Поиск заявок по имени --------------");
        String answer = this.input.ask("Введите имя заявки : ");
        Item[] item = this.tracker.findByName(answer);
        boolean flag = true;
        for (Item iter: item) {
            System.out.println("id: " + iter.getId());
            System.out.println("Имя заявки : " + iter.getName());
            System.out.println("Описание заявки :" + iter.getDesc());
            System.out.println("-------------------------------------------------");
            flag = false;
        }
        if (flag) {
            System.out.println("-------------------------------------------------");
        }
    }

    /**
     * Отображение меню в консоли.
     */
    private void showMenu() {
        String st = "Меню.\n0. Добавить новую заявку.\n"
                + "1. Показать все заявки.\n"
                + "2. Редактировать заявку.\n"
                + "3. Удалить заявку.\n"
                + "4. Найти заявку по Id.\n"
                + "5. Найти заявку по имени.\n"
                + "6. Выход из программы.\n";
        System.out.println(st);
    }

    /**
     * Запускт программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}