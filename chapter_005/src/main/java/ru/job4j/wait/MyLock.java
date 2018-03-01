package ru.job4j.wait;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MyLock {
    private final Object lock = new Object();
    private boolean freeLock = true;

    public void getLock() {
        synchronized (lock) {
            if (freeLock) {
                freeLock = false;
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void unlock() {
        synchronized (lock) {
            if (!freeLock) {
                freeLock = true;
                lock.notify();
            }
        }
    }
}
