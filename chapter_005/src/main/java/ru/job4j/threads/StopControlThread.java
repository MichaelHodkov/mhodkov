package ru.job4j.threads;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class StopControlThread {
    public void start(String st, int timeMSec) {
        Thread threadCount = new Thread(new Runnable() {
            @Override
            public void run() {
                CountChar countChar = new CountChar();
                countChar.counter(st);
            }
        });
        Thread threadTime = new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                do {
                    long currenTime = System.currentTimeMillis();
                    if (currenTime - startTime > timeMSec) {
                        threadCount.interrupt();
                    }
                } while (threadCount.isInterrupted());
            }
        });
        threadCount.start();
        threadTime.start();
    }

    public static void main(String[] args) {
        StopControlThread stopControlThread = new StopControlThread();
        String st = "3. Реализовать механизм программнной остановки потока.";
        stopControlThread.start(st, 1);
    }
}
