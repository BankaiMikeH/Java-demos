package net.gartee.bowling;

import net.gartee.messaging.SimpleEventAggregator;

public class GameBuilder {
    SuperFunBowling game = new SuperFunBowling(new SimpleEventAggregator());

    public static GameBuilder createGame() {
        return new GameBuilder();
    }

    public GameBuilder addPlayer(String name) {
        game.addPlayer(new Player(name));
        return this;
    }

    public SuperFunBowling build() {
        return game;
    }
}
