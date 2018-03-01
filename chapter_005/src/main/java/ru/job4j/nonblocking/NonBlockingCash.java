package ru.job4j.nonblocking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class NonBlockingCash {
    private ConcurrentHashMap<Integer, User> map = new ConcurrentHashMap<>();

    public void add(Integer key, User element) {
        map.put(key, element);
    }

    public void update(Integer key, User element) throws OplimisticException {
        User user = getElement(key);
        if (user != null) {
            int version = user.getVersion();
            if (!user.getName().equals(element.getName())) {
                if (version != user.getVersion()) {
                    throw new  OplimisticException("Oplimistic exception");
                }
                user.reName(element.getName());
            }
            if (!user.getValue().equals(element.getValue())) {
                if (version != user.getVersion()) {
                    throw new  OplimisticException("Oplimistic exception");
                }
                user.setValue(element.getValue());
            }
        }
    }

    public void delete(Integer key) {
        map.remove(key);
    }

    public User getElement(Integer key) {
        return map.get(key);
    }

    public void seeAll() {
        for (Map.Entry<Integer, User> entry : map.entrySet()) {
            System.out.println(String.valueOf(entry.getKey()).concat(" ").concat(String.valueOf(entry.getValue())));
        }
    }

    static class User {
        private String name;
        private String value;
        private int version;

        public User(String name, String value) {
            this.name = name;
            this.value = value;
            this.version = 0;
        }

        public void reName(String name) {
            this.name = name;
            version++;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public int getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return String.format("%s, %s", this.name, this.value);
        }
    }
}
