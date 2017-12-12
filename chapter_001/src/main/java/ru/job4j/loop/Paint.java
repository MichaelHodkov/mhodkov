package ru.job4j.loop;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Paint {

    /**
     * Метод рисует пирамиду из символа ^ и пробелов.
     * @param h - высота пирамиды.
     * @return строка с нарисованной пирамидой.
     */
    public String piramid(int h) {
        StringBuilder screen = new StringBuilder();
        if (h > 0) {
            int weight = 2 * h - 1;
            for (int row = 0; row != h; row++) {
                for (int column = 0; column != weight; column++) {
                    if (row >= h - column - 1 && row + h - 1 >= column) {
                        screen.append("^");
                    } else {
                        screen.append(" ");
                    }
                }
                final String line = System.getProperty("line.separator");
                screen.append(line);
            }
        }
        return screen.toString();
    };
}
