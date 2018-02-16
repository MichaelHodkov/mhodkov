package ru.job4j.set;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashSet<T> {
    private int size;
    private int defaultSize = 10;
    private Object[] hashArray;

    public SimpleHashSet() {
        this.size = 0;
        this.hashArray = new Object[defaultSize];
    }

    public SimpleHashSet(int capacity) {
        if (capacity < defaultSize) {
            capacity = defaultSize;
        }
        this.size = 0;
        this.hashArray = new Object[capacity];
    }

    public boolean add(T t) {
        Object value = hashArray[t.hashCode() % hashArray.length];
        if (value != null && value.equals(t)) {
            return false;
        }
        if (size >= hashArray.length || value != null) {
            resize(t, 0);
        }
        hashArray[t.hashCode() % hashArray.length] = t;
        size++;
        return true;
    }

    private void restartResize(T t, int capacity) {
        resize(t, capacity);
    }

    private void resize(T t, int bigProblem) {
        Object[] newHash = new Object[hashArray.length * 3 / 2 + 1 + bigProblem];
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                if (newHash[hashArray[i].hashCode() % newHash.length] != null) {
                    restartResize(t, newHash.length);
                    return;
                }
                newHash[hashArray[i].hashCode() % newHash.length] = hashArray[i];
            }
        }
        hashArray = newHash;
        add(t);
    }

    public boolean contains(T t) {
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null && hashArray[i].equals(t)) {
                return true;
            }
        }
        return false;
    }

    boolean remove(T t) {
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null && hashArray[i].equals(t)) {
                hashArray[i] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public int getCapacityAllItems() {
        int sum = 0;
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                sum++;
            }
        }
        return sum;
    }
}
