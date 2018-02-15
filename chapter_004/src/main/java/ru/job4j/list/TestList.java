package ru.job4j.list;

import java.util.ArrayList;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TestList {
    boolean hasCycle(Node node) {
        ArrayList<Node> list = new ArrayList<>();
        do {
            list.add(node);
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) == node) {
                    return true;
                }
            }
            node = node.next;
        } while (node != null);
        return false;
    }

    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    }
}
