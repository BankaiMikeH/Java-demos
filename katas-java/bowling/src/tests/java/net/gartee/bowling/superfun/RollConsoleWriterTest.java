package net.gartee.bowling.superfun;

import net.gartee.bowling.core.Game;
import net.gartee.bowling.boring.BoringBowling;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RollConsoleWriterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void roll_WithFakeGame_WritesToConsole() {
        Game fake = new FakeGame();
        RollConsoleWriter game = new RollConsoleWriter(fake);
        game.roll(1);

        assertThat(outContent.toString(), is("A 1 was rolled!"));
    }

    @Test
    public void roll_WithBoringGame_WritesToConsole() {
        RollConsoleWriter game = new RollConsoleWriter(new BoringBowling());
        game.roll(1);

        assertThat(outContent.toString(), is("A 1 was rolled!"));
    }

    private class FakeGame implements Game {
        public int timesCalled;

        @Override
        public void roll(int pins) {
            timesCalled++;
        }
    }
}