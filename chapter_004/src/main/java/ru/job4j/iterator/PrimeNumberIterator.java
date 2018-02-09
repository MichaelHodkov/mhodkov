package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class PrimeNumberIterator implements Iterator {
    private final int[] values;
    private int index = 0;

    public PrimeNumberIterator(int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        boolean flagNextPrime = false;
        for (int i = index; i < values.length; i++) {
            if (isItPrime(values[i])) {
                flagNextPrime = true;
                break;
            }
        }
        return flagNextPrime;
    }

    @Override
    public Object next() {
        do {
            if (index == values.length) {
                throw new NoSuchElementException("Not more prime numbers");
            }
        } while (!isItPrime(values[index++]));
        return values[index - 1];
    }

    private boolean isItPrime(int number) {
        boolean flagPrime = true;
        if (number >= 2) {
            if (number == 2) {
                return flagPrime;
            } else if (number % 2 == 0) {
                flagPrime = false;
            } else {
                for (int j = 3; j < number; j += 2) {
                    if (number % j == 0) {
                        flagPrime = false;
                        break;
                    }
                }
            }
        } else {
            flagPrime = false;
        }
        return flagPrime;
    }
}
