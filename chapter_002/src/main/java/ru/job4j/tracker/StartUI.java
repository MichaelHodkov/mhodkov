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
            String answer = this.input.ask("Chose menu: ");
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
        System.out.println("------------Add new Item--------------");
        String name = this.input.ask("Name:");
        String desc = this.input.ask("Description:");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("New ID: " + item.getId());
    }

    /**
     * Метод вывода всех заявок.
     */
    private void showItem() {
        System.out.println("------------All Items-----------------");
        boolean flag = true;
        for (Item iter: this.tracker.findAll()) {
            System.out.println("ID: " + iter.getId());
            System.out.println("Name: " + iter.getName());
            System.out.println("Description: " + iter.getDesc());
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
        System.out.println("------------Edit Item-----------------");
        String answer = this.input.ask("Enter ID: ");
        String name = this.input.ask("New name:");
        String desc = this.input.ask("New description:");
        Item item = new Item(name, desc);
        this.tracker.replace(answer, item);
        System.out.println("--------------------------------------");
    }

    /**
     * Метод удаления заявок.
     */
    private void deleteItem() {
        System.out.println("------------Delete Item---------------");
        String answer = this.input.ask("Enter ID: ");
        this.tracker.delete(answer);
        System.out.println("--------------------------------------");
    }

    /**
     * Метод поиска заявки по Id.
     */
    private void findId() {
        System.out.println("------------Find Item by ID-----------");
        String answer = this.input.ask("Enter ID: ");
        Item item = this.tracker.findById(answer);
        if (item != null) {
            System.out.println("ID: " + item.getId());
            System.out.println("Name: " + item.getName());
            System.out.println("Description: " + item.getDesc());
        }
        System.out.println("--------------------------------------");
    }

    /**
     * Метод поиска заявок по имени.
     */
    private void findName() {
        System.out.println("------------Find Item by Name---------");
        String answer = this.input.ask("Enter Item name: ");
        Item[] item = this.tracker.findByName(answer);
        boolean flag = true;
        for (Item iter: item) {
            System.out.println("ID: " + iter.getId());
            System.out.println("Name: " + iter.getName());
            System.out.println("Description: " + iter.getDesc());
            System.out.println("--------------------------------------");
            flag = false;
        }
        if (flag) {
            System.out.println("--------------------------------------");
        }
    }

    /**
     * Отображение меню в консоли.
     */
    private void showMenu() {
        String st = "Menu\n"
                + "0 Add new Item\n"
                + "1 Show all items\n"
                + "2 Edit item\n"
                + "3 Delete item\n"
                + "4 Find item by Id\n"
                + "5 Find items by name\n"
                + "6 Exit Program\n";
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