package net.gartee.bowling.messages;

public class PlayerRolled {
    private int pins;

    public PlayerRolled(int pins) {
        this.pins = pins;
    }

    public int getPins() {
        return pins;
    }
}
