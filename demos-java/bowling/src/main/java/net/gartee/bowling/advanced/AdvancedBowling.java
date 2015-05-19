package net.gartee.bowling.advanced;

import net.gartee.bowling.core.MultiPlayerGame;
import net.gartee.bowling.core.Player;

import java.util.*;

public class AdvancedBowling implements MultiPlayerGame, TrackedGame {
    public FrameFactory frameFactory;

    private List<Player> players = new ArrayList<>();
    private Map<Player, List<Frame>> frames = new HashMap<>();
    private Player currentPlayer;
    private Frame currentFrame;

    public AdvancedBowling(FrameFactory frameFactory) {
        this.frameFactory = frameFactory;
    }

    public int getPlayerScore(String playerName) {
        int score = 0;
        for(Frame frame : getPlayerFrames(playerName)) {
            score += frame.getScore();
        }
        return score;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        resetFrames();
        currentPlayer = getFirstPlayer();
        currentFrame = frames.get(currentPlayer).get(0);
    }

    public void resetFrames() {
        frames.clear();
        for(Player player : players) {
            frames.put(player, frameFactory.createFrames(player));
        }
    }

    public Player getPlayer(String playerName) {
        for(Player player : players) {
            if(playerName.equals(player.getName())) {
                return player;
            }
        }

        return null;
    }

    public List<Frame> getPlayerFrames(String playerName) {
        Player player = getPlayer(playerName);
        return frames.get(player);
    }

    public void roll(int pins) {
        currentFrame.addRoll(pins);

        if(currentFrame.isComplete()) {
            nextPlayer();
        }
    }

    public boolean isComplete() {
        for(Player player : players) {
            for(Frame frame : frames.get(player)) {
                if(!frame.isComplete()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void nextPlayer() {
        int nextFrameIndex = isLastPlayer()
            ? getNextFrameIndex()
            : getCurrentFrameIndex();

        currentPlayer = isLastPlayer()
            ? getFirstPlayer()
            : getNextPlayer();

        if(nextFrameIndex < 10) {
            nextFrame(nextFrameIndex);
        }
    }

    private void nextFrame(int nextFrameIndex) {
        currentFrame = frames.get(currentPlayer).get(nextFrameIndex);
    }

    private boolean isLastPlayer() {
        return players.indexOf(currentPlayer) == players.size() - 1;
    }

    private Player getFirstPlayer() {
        return players.get(0);
    }

    private Player getNextPlayer() {
        return players.get(players.indexOf(currentPlayer) + 1);
    }

    private int getCurrentFrameIndex() {
        return frames.get(currentPlayer).indexOf(currentFrame);
    }

    private int getNextFrameIndex() {
        return getCurrentFrameIndex() + 1;
    }
}