package ru.job4j.set;

import ru.job4j.list.ArrayContainer;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleSet<T> extends ArrayContainer<T> {
    @Override
    public void add(T o) {
        for (int i = 0; i < super.getSize(); i++) {
            if (super.get(i).equals(o)) {
                return;
            }
        }
        super.add(o);
    }
}
