package ru.job4j.department;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class DepartmentTest {
    Department department;
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void setUp() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("K2\\SK1\\SSK2");
        list.add("K1\\SK1\\SSK1");
        list.add("K1\\SK2");
        list.add("K1\\SK1\\SSK2");
        list.add("K2\\SK1\\SSK1");
        department = new Department(list);
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    @Test
    public void whenNormalSortedDepartament() {
        String expect = "K1\r\n"
                + "K1\\SK1\r\n"
                + "K1\\SK1\\SSK1\r\n"
                + "K1\\SK1\\SSK2\r\n"
                + "K1\\SK2\r\n"
                + "K2\r\n"
                + "K2\\SK1\r\n"
                + "K2\\SK1\\SSK1\r\n"
                + "K2\\SK1\\SSK2\r\n";
        department.print();
        assertThat(this.mem.toString(), is(expect));
    }

    @Test
    public void whenSortedByDecDepartament() {
        String expect = "K2\r\n"
                + "K2\\SK1\r\n"
                + "K2\\SK1\\SSK2\r\n"
                + "K2\\SK1\\SSK1\r\n"
                + "K1\r\n"
                + "K1\\SK2\r\n"
                + "K1\\SK1\r\n"
                + "K1\\SK1\\SSK2\r\n"
                + "K1\\SK1\\SSK1\r\n";
        department.printByDec();
        assertThat(this.mem.toString(), is(expect));
    }
}