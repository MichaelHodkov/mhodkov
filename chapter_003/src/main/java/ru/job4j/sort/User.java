package ru.job4j.sort;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class User implements Comparable<User> {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(User o) {
        int rsl = Integer.compare(this.age, o.age);
        return rsl != 0 ? rsl : this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return String.format("%s %d", this.name, this.age);
    }
}
