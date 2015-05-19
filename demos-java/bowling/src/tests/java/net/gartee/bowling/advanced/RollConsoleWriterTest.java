package net.gartee.bowling.advanced;

import net.gartee.bowling.core.Game;
import net.gartee.bowling.simple.SimpleBowling;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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
        Game fake = new MockGame();
        RollConsoleWriter game = new RollConsoleWriter(fake);
        game.roll(1);

        assertThat(outContent.toString(), is("A 1 was rolled!"));
    }

    @Test
    public void roll_WithBoringGame_WritesToConsole() {
        RollConsoleWriter game = new RollConsoleWriter(new SimpleBowling());
        game.roll(1);

        assertThat(outContent.toString(), is("A 1 was rolled!"));
    }

    @Test
    public void spyTest() {
        SimpleBowling gameSpy = Mockito.spy(new SimpleBowling());
        when(gameSpy.getScore()).thenReturn(0);

        gameSpy.roll(4);
        assertThat(gameSpy.getScore(), is(0));
    }

    private class MockGame implements Game {
        public int timesCalled;

        @Override
        public void roll(int pins) {
            timesCalled++;
        }
    }
}