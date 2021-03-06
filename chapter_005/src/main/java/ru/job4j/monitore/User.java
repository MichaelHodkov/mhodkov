package ru.job4j.monitore;

import java.util.Objects;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * amount - сумма денег на счете пользователя.
 */
public class User {
    private int id;
    private int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, amount);
    }
}
