package net.gartee.bowling.advanced;

import net.gartee.bowling.messages.PlayerRolled;
import net.gartee.bowling.core.Player;
import net.gartee.messaging.EventAggregator;
import net.gartee.messaging.EventSubscriber;

import java.util.ArrayList;
import java.util.List;

public class Frame implements EventSubscriber<PlayerRolled> {
    private static final int SPARE_BONUSES = 1;
    private static final int STRIKE_BONUSES = 2;
    private static final String MSG_PINS_BETWEEN_0_AND_10 = "pins must be a number between 0 and 10";

    private EventAggregator eventAggregator;

    protected Player owner;
    protected List<Integer> rolls = new ArrayList<>();
    private int bonus;

    public Frame(EventAggregator eventAggregator, Player owner) {
        this.eventAggregator = eventAggregator;
        this.owner = owner;
    }

    public void addRoll(int pins) {
        Guard.hasLegalPinCount(pins);

        rolls.add(pins);

        notifyPlayerRolled(pins);
        subscribeToFutureRolls();
    }

    private void subscribeToFutureRolls() {
        String eventGroup = owner.getName();
        if(isSpare()) {
            eventAggregator.subscribe(eventGroup, PlayerRolled.class, this, SPARE_BONUSES);
        }
        else if(isStrike()) {
            eventAggregator.subscribe(eventGroup, PlayerRolled.class, this, STRIKE_BONUSES);
        }
    }

    protected void notifyPlayerRolled(int pins) {
        String eventGroup = owner.getName();
        eventAggregator.send(eventGroup, new PlayerRolled(pins));
    }

    protected boolean isSpare() {
        return rolls.size() == 2 && rolls.get(0) + rolls.get(1) == 10;
    }

    protected boolean isStrike() {
        return rolls.size() == 1 && rolls.get(0) == 10;
    }

    public int getScore() {
        return rolls.stream().mapToInt(Integer::intValue).sum() + bonus;
    }

    public void onEvent(PlayerRolled message) {
        bonus += message.getPins();
    }

    public boolean isComplete() {
        return isStrike() || rolls.size() == 2;
    }

    private static class Guard {
        public static void hasLegalPinCount(int pins) {
            if(pins < 0 || pins > 10) {
                throw new IllegalArgumentException(MSG_PINS_BETWEEN_0_AND_10);
            }
        }
    }
}
