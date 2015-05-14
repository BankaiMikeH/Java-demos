package net.gartee.bowling;

public interface Game {
    void start();
    void roll(int pins);
    boolean isComplete();
}