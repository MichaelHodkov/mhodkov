package ru.job4j.tracker;

import java.util.Scanner;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class ConsoleInput implements Input {

    private Scanner scanner = new Scanner(System.in);

    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }
}
