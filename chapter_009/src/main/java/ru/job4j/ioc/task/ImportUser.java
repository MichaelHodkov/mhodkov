package ru.job4j.ioc.task;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ImportUser {
    public static void main(String[] args) {
        Storage<User> storage = new JdbcStorage("jdbc:postgresql://localhost:5432/spring", "postgres", "Qwerty123", true);
        User userOne = new User("TestOne");
        User userTwo = new User("TestTwo");
        storage.add(userOne);
        storage.add(userTwo);
        System.out.println(storage.getUsers());
    }
}
