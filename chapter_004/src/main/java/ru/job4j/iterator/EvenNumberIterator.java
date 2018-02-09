package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class EvenNumberIterator implements Iterator {
    private final int[] values;
    private int index = 0;

    public EvenNumberIterator(int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        for (int i = index; i < values.length; i++) {
            if (values[i] % 2 == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object next() {
        do {
            if (index == values.length) {
                throw new NoSuchElementException("Not more even numbers");
            }
        } while (values[index++] % 2 != 0);
        return values[index - 1];
    }
}
