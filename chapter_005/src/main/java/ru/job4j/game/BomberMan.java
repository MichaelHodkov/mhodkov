package ru.job4j.game;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BomberMan {
    private final ReentrantLock[][] board;

    public BomberMan(int size) {
        board = new ReentrantLock[size][size];
        init();
    }

    public BomberMan(int height, int width) {
        board = new ReentrantLock[height][width];
        init();
    }

    private void init() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
    }

    public void start() throws InterruptedException {
        Thread hero = new Thread(new HeroThread(board, new Position(0, 0), 500));
        Thread see = new Thread(new SeeBoardThread(this.board, 1000));
        see.start();
        hero.start();
        Thread.sleep(10000);
        hero.interrupt();
        see.interrupt();

    }
}
