public class PlayerRolled {
    private Player player;
    private int pins;

    public PlayerRolled(Player player, int pins) {
        this.player = player;

        this.pins = pins;
    }

    public int getPins() {
        return pins;
    }

    public Player getPlayer() {
        return player;
    }
}
