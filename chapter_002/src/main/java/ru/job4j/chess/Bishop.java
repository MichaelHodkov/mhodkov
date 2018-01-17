package ru.job4j.chess;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class Bishop extends Figure {

    Bishop(int x, int y) {
        super(x, y);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImposibleMoveException {
        if ((source.getX() + source.getY()) % 2 != (dest.getX() + dest.getY()) % 2) {
            throw new ImposibleMoveException("This figure does not move this...");
        }
        Cell[] arrWay = new Cell[7];
        int index = 0;
        int incrementX = 1;
        int incrementY = 1;
        int wayX = source.getX();
        int wayY = source.getY();
        if (dest.getY() < source.getY()) {
            incrementY = -1;
            if (dest.getX() < source.getX()) {
                incrementX = -1;
            }
        } else if (dest.getX() < source.getX()) {
            incrementX = -1;
        }
        do {
            wayX += incrementX;
            wayY += incrementY;
            if (wayX < 0 || wayX > 7 || wayY < 0 || wayY > 7) {
                throw new ImposibleMoveException("Not the correct diagonal...");
            }
            arrWay[index++] = new Cell(wayX, wayY);
        } while (!dest.cellEquals(new Cell(wayX, wayY)));
        return arrWay;
    }

    @Override
    public Figure copy(Cell dest) {
        return new Bishop(dest.getX(), dest.getY());
    }
}