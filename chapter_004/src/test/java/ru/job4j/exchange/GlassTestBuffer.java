package ru.job4j.exchange;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class GlassTestBuffer {
    Glass glass = new Glass();
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    @Test
    public void whenAddAndDelClaimInGlass() {
        glass.seeBid();
        assertThat(this.mem.toString(), is(""));
        glass.seeAsk();
        assertThat(this.mem.toString(), is(""));
        Claim testClaim = new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 145, 150);
        glass.insertClaim(testClaim);
        glass.seeBid();
        assertThat(this.mem.toString(), is(String.format("%s%n", testClaim.toString())));
        this.mem.reset();
        glass.insertClaim(new Claim("GAZ", Claim.Type.DEL, Claim.Action.BID, 145, 150));
        assertThat(this.mem.toString(), is(String.format("Заявка: (%s) удалена.%n", testClaim.toString())));
        this.mem.reset();
        glass.seeBid();
        assertThat(this.mem.toString(), is(""));
        glass.seeAsk();
        assertThat(this.mem.toString(), is(""));
        testClaim = new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 140, 200);
        glass.insertClaim(testClaim);
        glass.seeAsk();
        assertThat(this.mem.toString(), is(String.format("%s%n", testClaim.toString())));
        this.mem.reset();
        glass.insertClaim(new Claim("GAZ", Claim.Type.DEL, Claim.Action.ASK, 140, 200));
        assertThat(this.mem.toString(), is(String.format("Заявка: (%s) удалена.%n", testClaim.toString())));
        this.mem.reset();
        glass.seeAsk();
        assertThat(this.mem.toString(), is(""));
        testClaim = new Claim("GAZ", Claim.Type.ADD, Claim.Action.BID, 150, 2);
        Claim testClaimTwo = new Claim("GAZ", Claim.Type.ADD, Claim.Action.ASK, 145, 2);
        glass.insertClaim(testClaim);
        glass.insertClaim(testClaimTwo);
        this.mem.reset();
        glass.seeBid();
        glass.seeAsk();
        assertThat(this.mem.toString(), is(""));
    }
}