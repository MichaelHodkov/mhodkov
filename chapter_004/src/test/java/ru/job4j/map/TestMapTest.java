package ru.job4j.map;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TestMapTest {
    TestMap<UserAndHash> testMap = new TestMap<>();

    @Test
    public void whenCreatedTwoSameObjectUserWhereOverrideHashCodeAndPutInMap() {
        UserAndHash userOne = new UserAndHash("Name", 0, null);
        UserAndHash userTwo = new UserAndHash("Name", 0, null);
        System.out.println(String.format("Hash code User One: %d", userOne.hashCode()));
        System.out.println(String.format("Hash code User Two: %d", userTwo.hashCode()));
        System.out.print("User One equals user Two?: ");
        System.out.println(userOne.equals(userTwo));
        assertThat(userOne.equals(userTwo), is(false));
        testMap.put(userOne, 1);
        testMap.put(userTwo, 1);
        System.out.println(testMap.getMap());
    }
}