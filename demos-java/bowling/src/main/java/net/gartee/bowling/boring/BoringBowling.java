package net.gartee.bowling.boring;

import net.gartee.bowling.core.SinglePlayerGame;

public class BoringBowling implements SinglePlayerGame {
    private int[] rolls =new int[21];
    private int currentRoll;

    @Override
    public void roll(int pins) {
        rolls[currentRoll++] = pins;
    }

    @Override
    public int getScore() {
        int score = 0;
        int frameIndex = 0;

        for(int frame = 0; frame < 10; frame++) {

            if(isStrike(rolls[frameIndex])) {
                score += rolls[frameIndex] + getStrikeBonus(frameIndex);
                frameIndex++;
            }
            else if(isSpare(frameIndex)) {
                score += getStandardFrameScore(frameIndex) + getSpareBonus(frameIndex);
                frameIndex += 2;
            }
            else {
                score += getStandardFrameScore(frameIndex);
                frameIndex += 2;
            }
        }

        return score;
    }

    private int getSpareBonus(int frameIndex) {
        return rolls[frameIndex + 2];
    }

    private int getStrikeBonus(int frameIndex) {
        return rolls[frameIndex + 1] + rolls[frameIndex + 2];
    }

    private int getStandardFrameScore(int frameIndex) {
        return rolls[frameIndex] + rolls[frameIndex + 1];
    }

    private boolean isSpare(int frameIndex) {
        return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
    }

    private boolean isStrike(int roll) {
        return roll == 10;
    }
}
