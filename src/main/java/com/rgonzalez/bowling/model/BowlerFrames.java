package com.rgonzalez.bowling.model;

import java.util.Optional;
import java.util.stream.Stream;

public interface BowlerFrames {
    String getBowlerName();

    Optional<Integer> addRoll(Chance chance);

    boolean isFinished();

    Integer getScore();

    Stream<Frame> getFrames();
}
