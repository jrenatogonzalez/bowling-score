package com.rgonzalez.bowling.model;

import java.util.Optional;
import java.util.stream.Stream;

public interface BowlingScoring {
    Optional<Integer> addRoll(String bowlerName, int knockedDownPins);

    Stream<BowlerFrames> getBowlerFrames();

    boolean isFinished();
}
