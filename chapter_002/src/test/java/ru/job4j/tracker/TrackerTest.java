package ru.job4j.tracker;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class TrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription");
        tracker.add(item);
        assertThat(tracker.findAll()[0], is(item));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription");
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Item("test2", "testDescription2");
        // Проставляем старый id из previous, который был сгенерирован выше.
        next.setId(previous.getId());
        // Обновляем заявку в трекере.
        tracker.replace(previous.getId(), next);
        // Проверяем, что заявка с таким id имеет новые имя test2.
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenGenerateNewId() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        String idOne = tracker.findAll()[0].getId();
        String idTwo = tracker.findAll()[1].getId();
        boolean rsl = idOne.equals(idTwo) ? true : false;
        boolean expect = false;
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenDeleteSecondItemInArray() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        String key = item.getId();
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        tracker.delete(key);
        Item[] notNullArray = tracker.findAll();
        String[] rsl = new String[notNullArray.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = notNullArray[i].getName();
        }
        String[] expect = {"test1", "test3", "test4"};
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenDeleteFirstItemInArray() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        String key = item.getId();
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        tracker.delete(key);
        Item[] notNullArray = tracker.findAll();
        String[] rsl = new String[notNullArray.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = notNullArray[i].getName();
        }
        String[] expect = {"test2", "test3", "test4"};
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenDeleteLastItemInArray() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        String key = item.getId();
        tracker.delete(key);
        Item[] notNullArray = tracker.findAll();
        String[] rsl = new String[notNullArray.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = notNullArray[i].getName();
        }
        String[] expect = {"test1", "test2", "test3"};
        assertThat(rsl, is(expect));
    }

    @Test
    public void findAll() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        Item[] notNullArray = tracker.findAll();
        String[] rsl = new String[notNullArray.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = notNullArray[i].getName();
        }
        String[] expect = {"test1", "test2", "test3", "test4"};
        assertThat(rsl, is(expect));
    }

    @Test
    public void whenFindByName() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription1");
        tracker.add(item);
        item = new Item("test", "testDescription2");
        tracker.add(item);
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        item = new Item("test", "testDescription5");
        tracker.add(item);
        Item[] arrayFindByName = tracker.findByName("test");
        String[] rsl = new String[arrayFindByName.length];
        for (int i = 0; i < rsl.length; i++) {
            rsl[i] = arrayFindByName[i].getName();
        }
        String[] expect = {"test", "test", "test"};
        assertThat(rsl, is(expect));
    }

    @Test
    public void findById() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription1");
        tracker.add(item);
        item = new Item("test2", "testDescription2");
        tracker.add(item);
        Item expect = item;
        String key = item.getId();
        item = new Item("test3", "testDescription3");
        tracker.add(item);
        item = new Item("test4", "testDescription4");
        tracker.add(item);
        Item rsl = tracker.findById(key);
        assertThat(rsl, is(expect));
    }
}