package net.gartee.bowling.superfun;

import net.gartee.bowling.core.Player;
import net.gartee.messaging.EventAggregator;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StandardFrameFactoryTest {
    @Test
    public void createFrames_ReturnsNineFramesAndOneTenthFrame() {
        EventAggregator aggregator = mock(EventAggregator.class);

        FrameFactory factory = new StandardFrameFactory(aggregator);
        List<Frame> frames = factory.createFrames(new Player("Adam"));

        assertThat(frames.size(), is(10));
        assertTrue(frames.get(0) instanceof TenthFrame);
    }

}