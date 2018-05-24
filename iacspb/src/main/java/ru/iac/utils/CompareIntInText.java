package ru.iac.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CompareIntInText {
    public static int compare(String stOne, String stTwo) {
        if (stOne.equalsIgnoreCase(stTwo)) {
            return 0;
        } else {
            return compare(stOne.toLowerCase().toCharArray(), stTwo.toLowerCase().toCharArray());
        }
    }

    private static int compare(char[] charsOne, char[] charsTwo) {
        List<Object> wordOne = addWord(charsOne);
        List<Object> wordTwo = addWord(charsTwo);
        for (int i = 0; i < wordOne.size(); i++) {
            if (wordTwo.size() < i) {
                break;
            }
            Object objOne = wordOne.get(i);
            Object objTwo = wordTwo.get(i);
            if (objOne instanceof String) {
                if (objTwo instanceof String) {
                    int compr = ((String) objOne).compareTo((String) objTwo);
                    if (compr != 0) {
                        return compr;
                    }
                } else {
                    return -1;
                }
            } else {
                if (objTwo instanceof Integer) {
                    return Integer.compare((int) objOne, (int) objTwo);
                } else {
                    return 1;
                }
            }
        }
        if (wordOne.size() == wordTwo.size()) {
            return 0;
        } else if (wordOne.size() < wordTwo.size()){
            return 1;
        } else {
            return -1;
        }
    }

    private static List<Object> addWord(char[] chars) {
        List<Object> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        boolean flagStart = false;
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (isNum(ch)) {
                if (!flagStart) {
                    flagStart = true;
                    if (sb.length() > 0) {
                        list.add(sb.toString());
                        sb.setLength(0);
                    }
                }
                sum += Character.getNumericValue(ch);
            } else {
                if (flagStart) {
                    list.add(sum);
                    sum = 0;
                    flagStart = false;
                }
                sb.append(ch);
            }
        }
        if (flagStart) {
            list.add(sum);
        } else if (sb.length() > 0 ) {
            list.add(sb.toString());
        }
        return list;
    }


    private static boolean isNum(char ch) {
        return ch >= 48 && ch <= 58;
    }
}
