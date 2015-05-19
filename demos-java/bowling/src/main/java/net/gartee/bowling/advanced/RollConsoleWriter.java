package net.gartee.bowling.advanced;

import net.gartee.bowling.core.Game;

public class RollConsoleWriter implements Game {
    private final static String MESSAGE = "A %d was rolled!";
    private Game decorated;

    public RollConsoleWriter(Game game) {

        decorated = game;
    }

    @Override
    public void roll(int pins) {
        decorated.roll(pins);
        System.out.format(MESSAGE, pins);
    }
}
