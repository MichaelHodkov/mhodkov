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
public class RoleStoreTest {
    @Test
    public void whenAddRole() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("Admin"));
        roleStore.add(new Role("User"));
        roleStore.add(new Role("SC"));
        ArrayList<Role> expect = new ArrayList<Role>();
        expect.add(new Role("Admin"));
        expect.add(new Role("User"));
        expect.add(new Role("SC"));
        assertThat(roleStore.list.simpleList, is(expect));
    }
    @Test
    public void whenDeleteRole() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("Admin"));
        roleStore.add(new Role("User"));
        roleStore.add(new Role("SC"));
        roleStore.delete("SC");
        ArrayList<Role> expect = new ArrayList<Role>();
        expect.add(new Role("Admin"));
        expect.add(new Role("User"));
        assertThat(roleStore.list.simpleList, is(expect));
    }
    @Test
    public void whenReplaceRole() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("Admin"));
        roleStore.add(new Role("User"));
        roleStore.add(new Role("SC"));
        roleStore.replace("SC", new Role("Programmer"));
        ArrayList<Role> expect = new ArrayList<Role>();
        expect.add(new Role("Admin"));
        expect.add(new Role("User"));
        expect.add(new Role("Programmer"));
        assertThat(roleStore.list.simpleList, is(expect));
    }
    @Test
    public void whenFindByIdRole() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("Admin"));
        roleStore.add(new Role("User"));
        roleStore.add(new Role("SC"));
        assertThat(roleStore.findById("User"), is(new Role("User")));
    }
}