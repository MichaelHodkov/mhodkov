package ru.job4j.iterator;

import java.util.Iterator;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TwoDimensionalArrayIterator implements Iterator {
    private final int[][] values;
    private int indexX = 0;
    private int indexY = 0;

    public TwoDimensionalArrayIterator(final int[][] values) {
        this.values = values;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        boolean flagNextItem = false;
        if (indexX == values[indexY].length && indexY < values.length - 1) {
            return true;
        }
        return values.length > indexY && values[indexY].length > indexX;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Object next() {
        if (indexX == values[indexY].length) {
            indexY++;
            indexX = 0;
        }
        return values[indexY][indexX++];
    }
}
