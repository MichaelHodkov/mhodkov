package ru.job4j.tracker;

import java.util.Random;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Tracker {

    /**
     * Массив для хранение заявок.
     */
    private final Item[] items = new Item[100];

    /**
     * Указатель ячейки для новой заявки.
     */
    private int position = 0;

    private static final Random RN = new Random();


    /**
     * Метод добавляет заявку, переданную в аргументах в массив заявок this.items.
     * @param item - заявка.
     * @return - добавленную заявку.
     */
    public Item add(Item item) {
        item.setId(String.valueOf(this.generateId()));
        this.items[this.position++] = item;
        return item;
    };

    /**
     * Метод генерирует уникальный Id.
     * @return - возвращает сгенерированный Id.
     */
    String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    /**
     * Метод заменяет заявку в массиве this.items.
     * @param id - заменяемой заявки.
     * @param item - заявка на которую заменяем.
     */
    public void replace(String id, Item item) {
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getId().equals(id)) {
                String key = this.items[i].getId();
                item.setId(key);
                this.items[i] = item;
                break;
            }
        }
    };

    /**
     *  Метод удалит заявку в массиве this.items.
     * @param id - удаляемой заявки.
     */
    public void delete(String id) {
//        Item[] newItem = new Item[100];
//        System.arraycopy(this.items, 2, newItem, 1, position - 1 - 1);
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getId().equals(id)) {
                System.arraycopy(this.items, i + 1, this.items, i, (position - 1) - i);
                position--;
                break;
            }
        }
    };

    /**
     * Метод возвращает копию массива this.items без null элементов;
     * @return - массив заявок.
     */
    public Item[] findAll() {
        Item[] notNullArray = new Item[this.position];
        for (int i = 0; i < this.position; i++) {
            notNullArray[i] = this.items[i];
        }
        return notNullArray;
    };

    /**
     *  Метод возвращает все заявки по ключевому параметру в имени.
     * @param key - параметр по которому ищем.
     * @return - массив заявок содержащих ключ в имени.
     */
    public Item[] findByName(String key) {
        int count = 0;
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getName().equals(key)) {
                count++;
            }
        }
        Item[] findArray = new Item[count];
        int index = 0;
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getName().equals(key)) {
                findArray[index] = this.items[i];
                index++;
            }
        }
        return findArray;
    };

    /**
     * Метод ищет заявку по ID.
     * @param id - ключ по которому ищем заявку.
     * @return - заявку в которой ID соответсвует ключу.
     */
    public Item findById(String id) {
        Item findItem = null;
        for (int i = 0; i < this.position; i++) {
            if (this.items[i].getId().equals(id)) {
                findItem = this.items[i];
                break;
            }
        }
        return findItem;
    };
}
