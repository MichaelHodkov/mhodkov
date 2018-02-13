package ru.job4j.generic;

import org.junit.Test;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStoreTest {
    @Test
    public void whenAddUser() {
        UserStore userStore = new UserStore();
        userStore.add(new User("Mike"));
        userStore.add(new User("Ivan"));
        userStore.add(new User("Alex"));
        ArrayList<User> expect = new ArrayList<User>();
        expect.add(new User("Mike"));
        expect.add(new User("Ivan"));
        expect.add(new User("Alex"));
        assertThat(userStore.list.simpleList, is(expect));
    }
    @Test
    public void whenDeleteUser() {
        UserStore userStore = new UserStore();
        userStore.add(new User("Mike"));
        userStore.add(new User("Ivan"));
        userStore.add(new User("Alex"));
        userStore.delete("Ivan");
        ArrayList<User> expect = new ArrayList<User>();
        expect.add(new User("Mike"));
        expect.add(new User("Alex"));
        assertThat(userStore.list.simpleList, is(expect));
    }
    @Test
    public void whenReplaceUser() {
        UserStore userStore = new UserStore();
        userStore.add(new User("Mike"));
        userStore.add(new User("Ivan"));
        userStore.add(new User("Alex"));
        userStore.replace("Ivan", new User("Fedor"));
        ArrayList<User> expect = new ArrayList<User>();
        expect.add(new User("Mike"));
        expect.add(new User("Fedor"));
        expect.add(new User("Alex"));
        assertThat(userStore.list.simpleList, is(expect));
    }
    @Test
    public void whenFindByIdUser() {
        UserStore userStore = new UserStore();
        userStore.add(new User("Mike"));
        userStore.add(new User("Ivan"));
        userStore.add(new User("Alex"));
        assertThat(userStore.findById("Alex"), is(new User("Alex")));
    }

}