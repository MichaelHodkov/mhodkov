package ru.job4j.sort;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

import java.util.*;

public class SortUser {
    public Set<User> sort(List<User> list) {
        Set<User> treeSet = new TreeSet<User>();
        for (User user : list) {
            treeSet.add(user);
        }
        return treeSet;
    }
}
