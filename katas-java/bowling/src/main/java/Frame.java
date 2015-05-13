import java.util.ArrayList;
import java.util.List;

public class Frame implements EventSubscriber<PlayerRolled> {
    private static final int SPARE_BONUSES = 1;
    private static final int STRIKE_BONUSES = 2;

    private EventAggregator eventAggregator;

    private Player owner;
    private List<Integer> rolls = new ArrayList<>();
    private int bonus;

    public Frame(EventAggregator eventAggregator, Player owner) {
        this.eventAggregator = eventAggregator;
        this.owner = owner;
    }

    public void addRoll(int roll) {
        rolls.add(roll);
        doMessageSubscriptions();
    }

    private void doMessageSubscriptions() {
        String eventGroup = owner.getName();
        if(isSpare()) {
            eventAggregator.subscribe(eventGroup, PlayerRolled.class, this, SPARE_BONUSES);
        }
        else if(isStrike()) {
            eventAggregator.subscribe(eventGroup, PlayerRolled.class, this, STRIKE_BONUSES);
        }
    }

    private boolean isSpare() {
        return rolls.size() == 2 && rolls.get(0) + rolls.get(1) == 10;
    }

    private boolean isStrike() {
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
}
