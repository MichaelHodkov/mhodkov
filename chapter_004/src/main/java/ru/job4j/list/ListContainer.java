package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ListContainer<T> implements SimpleContainer<T> {
    private Node head;
    private Node feet;
    private int size;
    private int modCount;

    public ListContainer() {
        this.head = null;
        this.feet = null;
        this.size = 0;
        modCount = 0;

    }

    @Override
    public void add(T t) {
        Node node = new Node(t);
        if (head == null) {
            head = node;
            feet = node;
        } else {
            node.feet = this.feet;
            node.feet.head = node;
            feet = node;
        }
        size++;
        modCount++;
    }

    @Override
    public T get(int index) {
        int indexSearch = 0;
        Node node = head;
        while (indexSearch != index) {
            node = node.head;
            indexSearch++;
        }
        return (T) node.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node linkNext = head;
            private int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                return linkNext != null;
            }

            @Override
            public T next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("Iterator only for read.");
                } else if (linkNext == null) {
                    throw new NoSuchElementException("Not more elements.");
                }
                Object element = linkNext.item;
                linkNext = linkNext.head;
                return (T) element;
            }
        };
    }

    public int getSize() {
        return size;
    }

    private static class Node<E> {
        E item;
        Node<E> head;
        Node<E> feet;

        Node(E element) {
            this.item = element;
            this.head = null;
            this.feet = null;
        }
    }

    public static void main(String[] args) {
        ListContainer<Integer> listContainer = new ListContainer<>();
        listContainer.add(0);
        listContainer.add(1);
        listContainer.add(2);
        System.out.println(listContainer.get(2));
    }
}
