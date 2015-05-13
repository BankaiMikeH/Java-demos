public class GameBuilder {
    Game game = new Game(new SimpleEventAggregator());

    public static GameBuilder createGame() {
        return new GameBuilder();
    }

    public GameBuilder addPlayer(String name) {
        game.addPlayer(new Player(name));
        return this;
    }

    public Game build() {
        return game;
    }
}
