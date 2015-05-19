package net.gartee.bowling.util;

import net.gartee.bowling.core.Player;
import net.gartee.bowling.advanced.StandardFrameFactory;
import net.gartee.bowling.advanced.AdvancedBowling;
import net.gartee.messaging.SimpleEventAggregator;

public class GameBuilder {
    private AdvancedBowling game;

    private GameBuilder(AdvancedBowling game) {
        this.game = game;
    }

    public static GameBuilder createGame() {
        AdvancedBowling game = new AdvancedBowling(
            new StandardFrameFactory(new SimpleEventAggregator()));

        return new GameBuilder(game);
    }

    public GameBuilder addPlayer(String name) {
        game.addPlayer(new Player(name));
        return this;
    }

    public AdvancedBowling build() {
        return game;
    }
}
