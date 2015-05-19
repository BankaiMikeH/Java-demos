package net.gartee.bowling.advanced;

import net.gartee.bowling.core.Player;

import java.util.List;

public interface FrameFactory {
    List<Frame> createFrames(Player player);
}