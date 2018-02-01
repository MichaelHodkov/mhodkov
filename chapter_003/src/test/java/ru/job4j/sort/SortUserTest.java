package ru.job4j.sort;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortUserTest {

    @Test
    public void whenAddNotSorListInTree() {
        SortUser sortUser = new SortUser();
        List<User> list = new ArrayList<User>();
        list.addAll(
                Arrays.asList(
                        new User("Petr", 48),
                        new User("Mike", 22),
                        new User("Anton", 34),
                        new User("Vlad", 34),
                        new User("Alex", 22)
                )
        );
        Set<User> expect = new TreeSet<User>();
        expect.add(new User("Alex", 22));
        expect.add(new User("Mike", 22));
        expect.add(new User("Anton", 34));
        expect.add(new User("Vlad", 34));
        expect.add(new User("Petr", 48));
        assertThat(sortUser.sort(list), is(expect));
    }
}