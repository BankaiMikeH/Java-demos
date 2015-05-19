package net.gartee.bowling.util;

import net.gartee.bowling.core.Player;
import net.gartee.bowling.superfun.StandardFrameFactory;
import net.gartee.bowling.superfun.SuperFunBowling;
import net.gartee.messaging.SimpleEventAggregator;

public class GameBuilder {
    private SuperFunBowling game;

    private GameBuilder(SuperFunBowling game) {
        this.game = game;
    }

    public static GameBuilder createGame() {
        SuperFunBowling game = new SuperFunBowling(
            new StandardFrameFactory(new SimpleEventAggregator()));

        return new GameBuilder(game);
    }

    public GameBuilder addPlayer(String name) {
        game.addPlayer(new Player(name));
        return this;
    }

    public SuperFunBowling build() {
        return game;
    }
}
