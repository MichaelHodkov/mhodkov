package ru.job4j.threads;


/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CountChar {
    public void counter(String st) {
        new Thread() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) != 32) {
                        sum++;

                    }
                }
                System.out.println(String.format("Sum char = %d", sum));
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) == 32) {
                        sum++;

                    }
                }
                System.out.println(String.format("Sum space = %d", sum));
            }
        }.start();
    }

    public static void main(String[] args) {
        CountChar countChar = new CountChar();
        String st = "Создать программу, которая будет считать количество слов и пробелов в тексте.";
        countChar.counter(st);
    }
}

