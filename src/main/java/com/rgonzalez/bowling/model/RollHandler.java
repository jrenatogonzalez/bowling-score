package com.rgonzalez.bowling.model;

import java.util.Optional;
import java.util.stream.Stream;

public interface RollHandler {
    Optional<Integer> add(Chance chance);

    Optional<Integer> getKnockedDownPins(int rollIndex);

    Integer getTotalKnockedDownPins();

    Stream<Chance> getRolls();

    Integer size();

    boolean isFinished();

    Integer pinCapacity();
}
