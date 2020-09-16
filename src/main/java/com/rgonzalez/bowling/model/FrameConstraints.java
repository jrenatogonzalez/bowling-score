package com.rgonzalez.bowling.model;

public interface FrameConstraints {
    Integer getMaxPins();

    Integer getMaxRolls();

    Integer getMaxExtraRollsIfSpare();

    Integer getMaxExtraRollsIfStrike();

    Integer getMaxFrames();
}
