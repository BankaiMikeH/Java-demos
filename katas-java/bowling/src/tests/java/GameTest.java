import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void addPlayer_WithNewPlayer_AddsPlayer() throws Exception {
        Game game = GameBuilder.createGame()
                .addPlayer("Adam")
                .build();

        assertThat(game.getPlayer("Adam").getName(), is("Adam"));
    }

    @Test
    public void start_WithOnePlayer_SetsUpTheGame() {
        Game game = GameBuilder.createGame()
                .addPlayer("Adam")
                .build();

        game.start();

        List<Frame> playerFrames = game.getPlayerFrames("Adam");
        assertThat(playerFrames.size(), is(10));
    }

    @Test
    public void roll_WithSinglePlayerGameAllOnes_Scores20() {
        Game game = GameBuilder.createGame()
                .addPlayer("Adam")
                .build();

        game.start();
        for(int i = 0; i < 20; i++) {
            game.roll(1);
        }

        assertThat(game.getPlayerScore("Adam"), is(20));
    }

    @Test
    public void roll_WithSpare_ScoresWithSpareBonus() {
        Game game = GameBuilder.createGame()
                .addPlayer("Adam")
                .build();

        game.start();
        game.roll(5);
        game.roll(5);
        game.roll(1);

        assertThat(game.getPlayerScore("Adam"), is(12));
    }

    @Test
    public void roll_WithStrike_ScoresWithStrikeBonus() {
        Game game = GameBuilder.createGame()
                .addPlayer("Adam")
                .build();

        game.start();
        game.roll(10);
        game.roll(1);
        game.roll(1);

        assertThat(game.getPlayerScore("Adam"), is(14));
    }

    @Test
     public void roll_WithTurkey_ScoresWithStrikeBonuses() {
        Game game = GameBuilder.createGame()
                .addPlayer("Adam")
                .build();

        game.start();
        game.roll(10); // 30
        game.roll(10); // 21
        game.roll(10); // 12
        game.roll(1);
        game.roll(1);

        assertThat(game.getPlayerScore("Adam"), is(65));
    }

    @Test
    public void integration_WithMultiplePlayers_TracksPlayersAndFrames() {
        Game game = GameBuilder.createGame()
                .addPlayer("Adam")
                .addPlayer("Brett")
                .build();

        game.start();
        game.roll(10);

        game.roll(4);
        game.roll(4);

        game.roll(1);
        game.roll(1);

        game.roll(1);
        game.roll(1);

        assertThat(game.getPlayerScore("Adam"), is(14));
        assertThat(game.getPlayerScore("Brett"), is(10));
    }

    @Test
    public void integration_FullGameWithTestData_ScoresCorrectly() {
        Game game = GameBuilder.createGame()
                .addPlayer("Adam")
                .build();

        game.start();
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

        assertThat(game.getPlayerScore("Adam"), is(133));
    }
}