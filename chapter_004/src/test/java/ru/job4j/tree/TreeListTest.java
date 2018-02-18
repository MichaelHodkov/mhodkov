package ru.job4j.tree;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TreeListTest {
    @Test
    public void when6ElFindLastThen6() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
        assertThat(tree.isBinary(), is(false));
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
        assertThat(tree.isBinary(), is(true));
    }

    @Test
    public void whenManyItemsInTreeAndNotBinary() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(3, 7);
        tree.add(4, 5);
        tree.add(5, 6);
        tree.add(6, 8);
        tree.add(6, 9);
        tree.add(6, 10);
        assertThat(tree.isBinary(), is(false));
    }

    @Test
    public void whenManyItemsInTreeAndBinary() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(3, 7);
        tree.add(4, 5);
        tree.add(5, 6);
        tree.add(6, 8);
        tree.add(6, 9);
        assertThat(tree.isBinary(), is(true));
    }
}