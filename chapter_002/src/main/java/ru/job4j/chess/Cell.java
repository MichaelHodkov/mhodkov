package ru.job4j.chess;

import java.util.Objects;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Cell {
    private int x;
    private int y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Cell cell) {
        return this.x == cell.x && this.y == cell.y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
