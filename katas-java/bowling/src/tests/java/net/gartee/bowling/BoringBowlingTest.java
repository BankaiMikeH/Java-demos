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
}