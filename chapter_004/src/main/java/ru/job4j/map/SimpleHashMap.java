package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashMap<K, V> implements Iterable {
    private Object[][] array;
    private int defaultSize = 10;
    private int size = 0;
    private int modCount = 0;

    public SimpleHashMap(int capacity) {
        if (capacity < defaultSize) {
            capacity = defaultSize;
        }
        array = new Object[2][capacity];
    }

    public SimpleHashMap() {
        array = new Object[2][defaultSize];
    }

    public boolean insert(K key, V value) {
        int factor = array[0].length;
        if (size > factor * 2 / 3) {
            resize(0);
            factor = array[0].length;
        }
        int index = myHash(key, factor);
        if (array[0][index] != null) {
            return false;
        }
        array[0][index] = key;
        array[1][index] = value;
        modCount++;
        size++;
        return true;
    }

    private void resize(int bigProblem) {
        Object[][] newArray = new Object[2][array[0].length * 2 + bigProblem];
        int factor = newArray[0].length;
        for (int i = 0; i < array[0].length; i++) {
            if (array[0][i] != null) {
                int index = myHash((K) array[0][i], factor);
                if (newArray[0][index] != null) {
                    restart(factor);
                } else {
                    newArray[0][index] = array[0][i];
                    newArray[1][index] = array[1][i];
                }
            }
        }
        array = newArray;
    }

    private void restart(int factor) {
        resize(factor);
    }

    private int myHash(K key, int factor) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        h ^= (h >>> 2) ^ (h >>> 3);
        h %= factor;
        return h >= 0 ? h : h * -1;
    }

    public V get(K key) {
        for (int i = 0; i < array[0].length; i++) {
            if (array[0][i] != null && array[0][i].equals(key)) {
                return (V) array[1][i];
            }
        }
        return null;
    }

    public boolean delete(K key) {
        modCount++;
        for (int i = 0; i < array[0].length; i++) {
            if (array[0][i] != null && array[0][i].equals(key)) {
                array[0][i] = null;
                array[1][i] = null;
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return array[0].length;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            private int iterIndex = 0;
            private int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                while (iterIndex < getCapacity()) {
                    if (array[0][iterIndex] != null) {
                        return true;
                    } else {
                        iterIndex++;
                    }
                }
                return false;
            }

            @Override
            public V next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("Iterator only for read.");
                }
                if (iterIndex == getCapacity()) {
                    throw new NoSuchElementException("Not more elements.");
                }
                hasNext();
                return (V) array[1][iterIndex++];
            }
        };
    }

    public void see() {
        for (int i = 0; i < array[0].length; i++) {
            System.out.println("key: " + array[0][i] + ", value: " + array[1][i]);
        }
    }
}
