package ru.job4j.tracker;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class StubInput implements Input {

    private String[] answers;
    private int position = 0;

    public StubInput(String[] answers) {
        this.answers = answers;
    }

    public String ask(String question) {
        return answers[position++];
    }
}
