package com.rgonzalez.bowling.model;

import java.util.Optional;
import java.util.stream.Stream;

public interface Frame {
    Optional<Integer> addRoll(Chance chance);

    Optional<Integer> addExtraRoll(Chance chance);

    Integer getScore();

    void setPreviousFrameScore(Integer previousFrameScore);

    Integer getCumulativeScore();

    boolean isRollsFinished();

    boolean isFinished();

    boolean isStrike();

    boolean isSpare();

    Stream<Chance> getRolls();

    Stream<Chance> getExtraRolls();
}
