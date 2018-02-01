package ru.job4j.sort;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SortUser {
    public Set<User> sort(List<User> list) {
        Set<User> treeSet = new TreeSet<User>();
        for (User user : list) {
            treeSet.add(user);
        }
        return treeSet;
    }

    public List<User> sortNameLength(List<User> list) {
        List<User> newList = new ArrayList<>();
        for (User user : list) {
            newList.add(user);
        }
        newList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int rsl = Integer.compare(o1.getName().length(), o2.getName().length());
                return rsl != 0 ? rsl : o1.getName().compareTo(o2.getName());
            }
        });
        return newList;
    }

    public List<User> sortByAllFields(List<User> list) {
        List<User> newList = new ArrayList<>();
        for (User user : list) {
            newList.add(user);
        }
        newList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int rsl = o1.getName().compareTo(o2.getName());
                return rsl != 0 ? rsl : Integer.compare(o1.getAge(), o2.getAge());
            }
        });
        return newList;
    }
}
