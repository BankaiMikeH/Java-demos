package net.gartee.bowling.superfun;

import net.gartee.bowling.core.Player;
import net.gartee.messaging.EventAggregator;

import java.util.ArrayList;
import java.util.List;

public class StandardFrameFactory implements FrameFactory {
    private EventAggregator eventAggregator;

    public StandardFrameFactory(EventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;
    }

    public List<Frame> createFrames(Player player) {
        ArrayList<Frame> frames = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            frames.add(new Frame(eventAggregator, player));
        }

        frames.add(new TenthFrame(eventAggregator, player));
        return frames;
    }
}
