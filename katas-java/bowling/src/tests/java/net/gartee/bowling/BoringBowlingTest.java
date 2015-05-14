package net.gartee.bowling;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class BoringBowlingTest {
    @Test
    public void getScore_WithGutterGame_Returns0() {
        BoringBowling game = new BoringBowling();
        for(int i = 0; i < 21; i++) {
            game.roll(0);
        }

        assertThat(game.getScore(), is(0));
    }

    @Test
    public void getScore_WithAllOnes_Returns20() {
        BoringBowling game = new BoringBowling();
        for(int i = 0; i < 20; i++) {
            game.roll(1);
        }

        assertThat(game.getScore(), is(20));
    }

    @Test
    public void getScore_WithSpare_ReturnsScoreWithSpareBonus() {
        BoringBowling game = new BoringBowling();
        game.roll(5);
        game.roll(5);
        game.roll(1);

        assertThat(game.getScore(), is(12));
    }

    @Test
    public void getScore_WithStrike_ReturnsScoreWithStrikeBonus() {
        BoringBowling game = new BoringBowling();
        game.roll(10);
        game.roll(1);
        game.roll(1);

        assertThat(game.getScore(), is(14));
    }

    @Test
    public void getScore_WithFullGame_ReturnsCorrectScore() {
        BoringBowling game = new BoringBowling();
        rollFullGame(game);

        assertThat(game.getScore(), is(133));
    }

    private void rollFullGame(BoringBowling game) {
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