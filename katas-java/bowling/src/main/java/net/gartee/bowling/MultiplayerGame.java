package net.gartee.bowling;

import java.util.List;

public interface MultiPlayerGame extends Game {
    void addPlayer(Player player);
    void start();
    int getPlayerScore(String playerName);
    List<Frame> getPlayerFrames(String playerName);
    boolean isComplete();
}