package ru.job4j.array;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Contains {

    /**
     * Метод проверяет что словo sub находится в другом слове origin.
     * @param origin - слово где ищем.
     * @param sub - слово которое ищем.
     * @return true - нашли слово, false - не нашли слово.
     */
    public boolean contains(String origin, String sub) {
        if (sub.length() > origin.length()) {
            return false;
        }
        char[] origArray = origin.toCharArray();
        char[] subArray = sub.toCharArray();
        int count = 0;
        int index = 0;
        for (int i = 0; i < origArray.length - 1; i++) {
            if (origArray[i] == subArray[index]) {
                count++;
                index++;
                if (count == subArray.length) {
                    return true;
                }
                if (index == subArray.length) {
                    return false;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }
}
