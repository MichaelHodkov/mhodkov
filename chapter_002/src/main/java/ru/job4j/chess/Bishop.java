package ru.job4j.chess;

public class Bishop extends Figure {

    public Bishop(int x, int y) {
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
            if (dest.getX() < source.getX()) {
                incrementX = -1;
                incrementY = -1;
            } else {
                incrementY = -1;
            }
        } else if (dest.getX() < source.getX()) {
            incrementX = -1;
        }
        do {
            wayX += incrementX ;
            wayY += incrementY;
            if (wayX < 0 || wayX > 7 || wayY < 0 || wayY > 7) {
                throw new ImposibleMoveException("Not the correct diagonal...");
            }
            arrWay[index++] = new Cell(wayX, wayY);
        } while (!dest.equals(new Cell(wayX, wayY)));
        //while (source.x != dest.x || source.y != dest.y);
        return arrWay;
    }

    @Override
    public Figure copy(Cell dest) {
        return new Bishop(dest.getX(), dest.getY());
    }
}