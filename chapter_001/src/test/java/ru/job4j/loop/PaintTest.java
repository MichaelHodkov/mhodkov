package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PaintTest {

    @Test
    public void whenHeightThree() {
        Paint paint = new Paint();
        String rsl = paint.piramid(3);
        final String ln = System.getProperty("line.separator");
        assertThat(rsl, is(
                String.format("  ^  %s ^^^ %s^^^^^%s", ln, ln, ln)
                )
        );
    }

    @Test
    public void whenHeightFive() {
        Paint paint = new Paint();
        String rsl = paint.piramid(5);
        final String ln = System.getProperty("line.separator");
        assertThat(rsl, is(
                String.format("    ^    %s   ^^^   %s  ^^^^^  %s ^^^^^^^ %s^^^^^^^^^%s", ln, ln, ln, ln, ln)
                )
        );
    }

}