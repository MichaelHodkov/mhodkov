package ru.job4j.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TreeList<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;

    public TreeList(E element) {
        Node<E> node = new Node<>(element);
        this.root = node;
    }

    @Override
    public boolean add(E parent, E child) {
        Optional<Node<E>> oNode = findBy(parent);
        if (oNode == null) {
            return false;
        }
        Node<E> node = oNode.get();
        for (Node<E> tempNode : node.leaves()) {
            if (tempNode.eqValue(child)) {
                return false;
            }
        }
        Node<E> childNode = new Node<>(child);
        node.add(childNode);
        return true;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}
