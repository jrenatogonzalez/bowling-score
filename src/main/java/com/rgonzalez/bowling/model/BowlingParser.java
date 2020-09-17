package com.rgonzalez.bowling.model;

import java.util.stream.Stream;

public interface BowlingParser {
    Stream<BowlerResults> getResults();
}
