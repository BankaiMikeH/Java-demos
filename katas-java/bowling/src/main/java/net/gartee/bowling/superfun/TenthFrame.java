package net.gartee.bowling.superfun;

import net.gartee.bowling.core.Player;
import net.gartee.messaging.EventAggregator;

public class TenthFrame extends Frame {

    public TenthFrame(EventAggregator eventAggregator, Player owner) {
        super(eventAggregator, owner);
    }

    @Override
    public void addRoll(int pins) {
        rolls.add(pins);
        notifyPlayerRolled(pins);
    }

    @Override
    public boolean isComplete() {
        return rolls.size() == 2 && getScore() < 10
            || rolls.size() == 3;
    }
}
