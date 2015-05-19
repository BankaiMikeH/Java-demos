package net.gartee.bowling.simple;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBowlingTest {
    @Test
    public void getScore_WithGutterGame_Returns0() {
        SimpleBowling game = new SimpleBowling();
        for(int i = 0; i < 21; i++) {
            game.roll(0);
        }

        assertThat(game.getScore(), is(0));
    }

    @Test
    public void getScore_WithAllOnes_Returns20() {
        SimpleBowling game = new SimpleBowling();
        for(int i = 0; i < 20; i++) {
            game.roll(1);
        }

        assertThat(game.getScore(), is(20));
    }

    @Test
    public void getScore_WithSpare_ReturnsScoreWithSpareBonus() {
        SimpleBowling game = new SimpleBowling();
        game.roll(5);
        game.roll(5);
        game.roll(1);

        assertThat(game.getScore(), is(12));
    }

    @Test
    public void getScore_WithStrike_ReturnsScoreWithStrikeBonus() {
        SimpleBowling game = new SimpleBowling();
        game.roll(10);
        game.roll(1);
        game.roll(1);

        assertThat(game.getScore(), is(14));
    }

    @Test
    public void getScore_WithFullGame_ReturnsCorrectScore() {
        SimpleBowling game = new SimpleBowling();
        rollFullGame(game);

        assertThat(game.getScore(), is(133));
    }

    private void rollFullGame(SimpleBowling game) {
        game.roll(1);
        game.roll(4);

        game.roll(4);
        game.roll(5);

        game.roll(6);
        game.roll(4);

        game.roll(5);
        game.roll(5);

        game.roll(10);

        game.roll(0);
        game.roll(1);

        game.roll(7);
        game.roll(3);

        game.roll(6);
        game.roll(4);

        game.roll(10);

        game.roll(2);
        game.roll(8);
        game.roll(6);
    }
}