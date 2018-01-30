package ru.job4j.chess;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.figures[0][7] = new Bishop(0, 7);
        print(board);
        board.move(new Cell(0, 7), new Cell(7, 0));
        print(board);
//        int x = 1;
//        int y = 1;
//        System.out.println(String.format("%d %d = %d", x, y, Integer.compare(x,y)));
//        x = 1;
//        y = 0;
//        System.out.println(String.format("%d %d = %d", x, y, Integer.compare(x,y)));
//        x = 0;
//        y = 1;
//        System.out.println(String.format("%d %d = %d", x, y, Integer.compare(x,y)));
    }

    public static void print(Cell[] mas) {
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] != null) {
                System.out.println(String.format("way[x][y] = [%d][%d]", mas[i].getX(), mas[i].getY()));
            }
        }
        System.out.println();
    }

    public static void print(Board board) {
        System.out.println("________________________");
        int figure = 0;
        for (int y = 0; y < board.figures.length; y++) {
            for (int x = 0; x < board.figures.length; x++) {
                if (board.figures[x][y] != null) {
                    if (board.figures[x][y] instanceof Bishop) {
                        figure = 8;
                    } else if (board.figures[x][y] instanceof Knight) {
                        figure = 9;
                    } else if (board.figures[x][y] instanceof Rook) {
                        figure = 4;
                    } else if (board.figures[x][y] instanceof Pawn) {
                        figure = 2;
                    } else if (board.figures[x][y] instanceof King) {
                        figure = 5;
                    } else if (board.figures[x][y] instanceof Queen) {
                        figure = 7;
                    }
                    System.out.print(String.format("|%d|", figure));
                } else {
                    System.out.print("| |");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------");
        System.out.println();
    }
}
