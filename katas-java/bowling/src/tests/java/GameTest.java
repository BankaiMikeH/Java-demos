import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GameTest {

    private static final String PLAYER1 = "Player1";
    private static final String PLAYER2 = "Player2";

    @Test
    public void addPlayer_WithNewPlayer_AddsPlayer() {

        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        assertThat(game.getPlayer(PLAYER1).getName(), is(PLAYER1));
    }

    @Test
    public void start_WithOnePlayer_SetsUpTheGame() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        game.start();

        List<Frame> playerFrames = game.getPlayerFrames(PLAYER1);
        assertThat(playerFrames.size(), is(10));
    }

    @Test
    public void start_WithOnePlayer_SetsUpFrames() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        game.start();

        assertThat(game.getPlayerFrames(PLAYER1).size(), is(10));
        assertTrue(game.getPlayerFrames(PLAYER1).get(9) instanceof TenthFrame);
    }

    @Test
    public void roll_WithSinglePlayerGameAllOnes_Scores20() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        game.start();
        for(int i = 0; i < 20; i++) {
            game.roll(1);
        }

        assertThat(game.getPlayerScore(PLAYER1), is(20));
    }

    @Test
    public void roll_WithSpare_ScoresWithSpareBonus() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        game.start();
        game.roll(5);
        game.roll(5);
        game.roll(1);

        assertThat(game.getPlayerScore(PLAYER1), is(12));
    }

    @Test
    public void roll_WithStrike_ScoresWithStrikeBonus() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        game.start();
        game.roll(10);
        game.roll(1);
        game.roll(1);

        assertThat(game.getPlayerScore(PLAYER1), is(14));
    }

    @Test
    public void roll_WithTurkey_ScoresWithStrikeBonuses() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        game.start();
        game.roll(10); // 30
        game.roll(10); // 21
        game.roll(10); // 12
        game.roll(1);
        game.roll(1);

        assertThat(game.getPlayerScore(PLAYER1), is(65));
    }

    @Test
    public void integration_WithMultiplePlayers_TracksPlayersAndFrames() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .addPlayer(PLAYER2)
                .build();

        game.start();
        game.roll(10);

        game.roll(4);
        game.roll(4);

        game.roll(1);
        game.roll(1);

        game.roll(1);
        game.roll(1);

        assertThat(game.getPlayerScore(PLAYER1), is(14));
        assertThat(game.getPlayerScore(PLAYER2), is(10));
    }

    @Test
    public void integration_FullGameWithTestData_ScoresGameCorrectly() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        game.start();
        rollFullGame(game);

        assertThat(getFrameScore(game, PLAYER1, 0).getScore(), is(5));
        assertThat(getFrameScore(game, PLAYER1, 1).getScore(), is(9));
        assertThat(getFrameScore(game, PLAYER1, 2).getScore(), is(15));
        assertThat(getFrameScore(game, PLAYER1, 3).getScore(), is(20));
        assertThat(getFrameScore(game, PLAYER1, 4).getScore(), is(11));
        assertThat(getFrameScore(game, PLAYER1, 5).getScore(), is(1));
        assertThat(getFrameScore(game, PLAYER1, 6).getScore(), is(16));
        assertThat(getFrameScore(game, PLAYER1, 7).getScore(), is(20));
        assertThat(getFrameScore(game, PLAYER1, 8).getScore(), is(20));
        assertThat(getFrameScore(game, PLAYER1, 9).getScore(), is(16));

        assertThat(game.getPlayerScore(PLAYER1), is(133));
    }

    @Test
    public void integration_FullGameWithTestData_CompletesFrames() {
        Game game = GameBuilder.createGame()
                .addPlayer(PLAYER1)
                .build();

        game.start();
        rollFullGame(game);

        assertThat(game.getPlayerFrames(PLAYER1).get(0).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(1).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(2).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(3).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(4).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(5).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(6).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(7).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(8).isComplete(), is(true));
        assertThat(game.getPlayerFrames(PLAYER1).get(9).isComplete(), is(true));

        assertThat(game.isComplete(), is(true));
    }

    private Frame getFrameScore(Game game, String playerName, int frameIndex) {
        return game.getPlayerFrames(playerName).get(frameIndex);
    }

    private void rollFullGame(Game game) {
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