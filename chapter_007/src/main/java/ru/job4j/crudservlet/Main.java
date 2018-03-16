package ru.job4j.crudservlet;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        UserStore userStore = UserStore.getInstance();
        for (User user : userStore.getUsers()) {
            System.out.println(user);
        }

        UserStore userStore2 = UserStore.getInstance();
        for (User user : userStore2.getUsers()) {
            System.out.println(user);
        }
    }
}
