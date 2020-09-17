package com.rgonzalez.bowling.model;

import java.util.stream.Stream;

public interface BowlingScorePrinter {
    Stream<String> printScore(Stream<BowlerFrames> bowlerFramesStream);
}
