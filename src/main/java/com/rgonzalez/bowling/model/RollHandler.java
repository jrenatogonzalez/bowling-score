package com.rgonzalez.bowling.model;

import java.util.Optional;
import java.util.stream.Stream;

public interface RollHandler {
    Optional<Integer> add(int knockedDownPins);

    Optional<Integer> getKnockedDownPins(int rollIndex);

    Integer getTotalKnockedDownPins();

    Stream<Integer> getRolls();

    Integer size();

    boolean isFinished();

    Integer pinCapacity();
}
