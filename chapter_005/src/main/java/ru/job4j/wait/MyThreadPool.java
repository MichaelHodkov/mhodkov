package ru.job4j.wait;

import java.util.LinkedList;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MyThreadPool {
    private final MyThread[] threads;
    private final LinkedList<Work> queueWork = new LinkedList<>();

    public MyThreadPool() {
        int cores = Runtime.getRuntime().availableProcessors();
        threads = new MyThread[cores];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread();
            threads[i].start();

        }
    }

    public void add(Work work) {
        synchronized (queueWork) {
            queueWork.add(work);
            queueWork.notifyAll();
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            synchronized (queueWork) {
                while (true) {
                    if (queueWork.size() > 0) {
                        queueWork.poll().run();
                    } else {
                        try {
                            queueWork.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class Work implements Runnable {
        @Override
        public void run() {
            System.out.println("Todo...");
            int sum = 0;
            for (int i = 0; i < 100000; i++) {
                sum += i;
            }
            System.out.println(String.format("Sum = %d", sum));
        }
    }
}
