package com.rgonzalez.bowling.model;

import java.util.Optional;
import java.util.stream.Stream;

public interface Frame {
    Optional<Integer> addRoll(Integer knockedDownPins);

    Optional<Integer> addExtraRoll(Integer knockedDownPins);

    Integer getScore();

    void setPreviousFrameScore(Integer previousFrameScore);

    Integer getCumulativeScore();

    boolean isRollsFinished();

    boolean isFinished();

    boolean isStrike();

    boolean isSpare();

    Stream<Integer> getRolls();

    Stream<Integer> getExtraRolls();
}
