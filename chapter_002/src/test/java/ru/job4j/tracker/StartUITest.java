package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class StartUITest {
    // получаем ссылку на стандартный вывод в консоль.
    private final PrintStream stdout = System.out;
    // Создаем буфур для хранения вывода.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private String stMenu = "Menu\n"
            + "0 Add new Item\n"
            + "1 Show all items\n"
            + "2 Edit item\n"
            + "3 Delete item\n"
            + "4 Find item by Id\n"
            + "5 Find items by name\n"
            + "6 Exit Program\n\r\n";

    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        //Заменяем стандартный вывод на вывод в пямять для тестирования.
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        // возвращаем обратно стандартный вывод в консоль.
        System.setOut(this.stdout);
        System.out.println("execute after method");
    }


    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();     // создаём Tracker
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});   //создаём StubInput с последовательностью действий
        new StartUI(input, tracker).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.findAll()[0].getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    @Test
    public void whenUsedShowAllItems() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name1", "desc1"));
        String key = item.getId();
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        String stID = "------------All Items-----------------\r\n"
                + "id: "
                + key
                + "\r\n"
                + "Name: name1\r\n"
                + "Description:desc1\r\n"
                + "--------------------------------------\r\n";
        String stExpect = stMenu + stID + stMenu;
        assertThat(new String(out.toByteArray()), is(stExpect));
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("name", "dec"));
        //создаём StubInput с последовательностью действий
        Input input = new StubInput(new String[]{"2", item.getId(), "test name", "desc", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
    }

    @Test
    public void whenDeleteItem() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("name1", "desc1"));
        String key = item.getId();
        tracker.add(new Item("name2", "desc2"));
        Input input = new StubInput(new String[]{"3", key, "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("name2"));
    }

    @Test
    public void whenFindById() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("name1", "desc1"));
        Item item = tracker.add(new Item("name2", "desc2"));
        String key = item.getId();
        tracker.add(new Item("name3", "desc3"));
        Input input = new StubInput(new String[]{"4", key, "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(key).getName(), is("name2"));
    }

    @Test
    public void whenFindByName() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("name1", "desc1"));
        tracker.add(new Item("name", "desc2"));
        tracker.add(new Item("name3", "desc3"));
        tracker.add(new Item("name", "desc4"));
        tracker.add(new Item("name5", "desc5"));
        Input input = new StubInput(new String[]{"5", "name", "6"});
        new StartUI(input, tracker).init();
        Item[] expect = new Item[2];
        expect[0] = tracker.findAll()[1];
        expect[1] = tracker.findAll()[3];
        assertThat(tracker.findByName("name"), is(expect));
    }

}