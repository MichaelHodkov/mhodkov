package ru.job4j;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConvertListTest {
    private ConvertList convertList = new ConvertList();

    @Test
    public void whenConvertArrayInList() {
        int[][] arr = {{1, 7, 9}, {8, 4, 3}, {45}};
        List<Integer> testList = convertList.toList(arr);
        List<Integer> expectList = new ArrayList<>();
        expectList.add(1);
        expectList.add(7);
        expectList.add(9);
        expectList.add(8);
        expectList.add(4);
        expectList.add(3);
        expectList.add(45);
        assertThat(testList, is(expectList));
    }

    @Test
    public void whenConvertListToArray() {
        List<Integer> testList = new ArrayList<>();
        testList.add(1);
        testList.add(7);
        testList.add(9);
        testList.add(8);
        testList.add(4);
        testList.add(3);
        testList.add(45);
        int[][] testArray = convertList.toArray(testList, 3);
        int[][] expectArray = {{1, 7, 9}, {8, 4, 3}, {45, 0, 0}};
        assertThat(testArray, is(expectArray));
    }
}