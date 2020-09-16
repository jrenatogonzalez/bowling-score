package com.rgonzalez.bowling.model;

import java.util.Optional;
import java.util.stream.Stream;

public interface BowlerFrames {
    String getBowlerName();

    Optional<Integer> addRoll(int knockedDownPins);

    boolean isFinished();

    Integer getScore();

    Stream<Frame> getFrames();
}
