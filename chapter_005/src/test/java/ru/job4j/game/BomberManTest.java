package ru.job4j.game;

import org.junit.Test;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BomberManTest {
    @Test
    public void whenTestBomberMan() throws InterruptedException {
        BomberMan bomberMan = new BomberMan(5, 10, Difficulty.Dif.EASY, Difficulty.Dif.HARD);
        bomberMan.start();
    }

}