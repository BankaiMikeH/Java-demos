package net.gartee.bowling.advanced;

import net.gartee.bowling.core.Player;
import net.gartee.bowling.messages.PlayerRolled;
import net.gartee.messaging.EventAggregator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class DemoTests {

    private static final String PLAYER_NAME = "Adam";

    // Stub
    @Test
    public void start_WithStubFrameFactory_SetsUpGameWithFakeFrames() {
        FrameFactory frameFactory = mock(FrameFactory.class);
        when(frameFactory.createFrames(anyObject()))
            .thenReturn(Arrays.asList(new FakeFrame()));

        AdvancedBowling game = new AdvancedBowling(frameFactory);
        game.addPlayer(new Player(PLAYER_NAME));
        game.start();

        assertThat(game.getPlayerFrames(PLAYER_NAME).size(), is(1));
        assertTrue(game.getPlayerFrames(PLAYER_NAME).get(0) instanceof FakeFrame);
    }

    // Mock (with assert)
    @Test
    public void addRoll_WithStrike_SubscribesToPlayerRollMessage() {
        EventAggregator eventAggregator = mock(EventAggregator.class);

        Frame frame = new Frame(eventAggregator, new Player(PLAYER_NAME));
        frame.addRoll(10);

        verify(eventAggregator).subscribe(PLAYER_NAME, PlayerRolled.class, frame, 2);
    }

    // Spy (Wrapper / Fake)
    @Test
    public void getPlayerScore_WithSpyWrapper_ScoresFakeRollsRegardlessOfGameSetup() {
        AdvancedBowling game = new AdvancedBowlingWrapper();

        game.addPlayer(new Player(PLAYER_NAME));
        game.start();
        game.roll(1);

        assertThat(game.getPlayerScore("nobody"), is(5));
    }

    // Spy (Mockito)
    @Test
    public void getPlayerScore_WithMockitoSpy_ScoresFakeRollsRegardlessOfGameSetup() {
        FrameFactory frameFactory = mock(FrameFactory.class);
        when(frameFactory.createFrames(anyObject()))
            .thenReturn(Arrays.asList(new FakeFrame()));

        AdvancedBowling game = spy(new AdvancedBowling(frameFactory));
        game.addPlayer(new Player(PLAYER_NAME));
        when(game.getPlayerFrames(anyString()))
            .thenReturn(Arrays.asList(new FakeFrame(4, 1)));

        game.start();
        game.roll(1);

        assertThat(game.getPlayerScore("nobody"), is(5));
    }






    public class AdvancedBowlingWrapper extends AdvancedBowling{
        public AdvancedBowlingWrapper() {
            super(null);
        }

        @Override
        public List<Frame> getPlayerFrames(String playerName) {
            List<Frame> frames = new ArrayList<>();
            frames.add(new FakeFrame(4, 1));
            return frames;
        }

        @Override
        public void resetFrames() {
            // nothing
        }

        @Override
        protected Frame getCurrentFrame() {
            return new FakeFrame(4, 1);
        }
    }

    public class FakeFrame extends Frame {
        public FakeFrame() {
            super(null, null);
        }

        public FakeFrame(int firstRoll, int secondRoll) {
            super(null, null);
            rolls.add(firstRoll);
            rolls.add(secondRoll);
        }

        @Override
        public void addRoll(int pins) {
            rolls.add(pins);
        }
    }
}
