package net.gartee.bowling.core;

public interface MultiPlayerGame extends Game {
    void addPlayer(Player player);
    int getPlayerScore(String playerName);
}