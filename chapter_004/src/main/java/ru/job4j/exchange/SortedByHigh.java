package ru.job4j.exchange;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 * */
public class SortedByHigh implements Comparator<LinkedList<Claim>> {

    @Override
    public int compare(LinkedList<Claim> o1, LinkedList<Claim> o2) {
        return Float.compare(o1.element().getPrice(), o2.element().getPrice()) * -1;
    }
}
