package ru.job4j.game;

import org.junit.Test;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BomberManTest {
    @Test
    public void whenTestBomberMan() {
        BomberMan bomberMan = new BomberMan(5);
        bomberMan.start();
    }

}