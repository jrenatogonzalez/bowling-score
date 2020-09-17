package com.rgonzalez.bowling.model;

import java.util.stream.Stream;

public class DefaultBowlingScorePrinter implements BowlingScorePrinter {

    @Override
    public Stream<String> printScore(Stream<BowlerFrames> bowlerFramesStream) {
        return bowlerFramesStream.map(this::getFinalScore);
    }

    private String getFinalScore(BowlerFrames bowlerFrames) {
        return String.format("%s: %d",
                bowlerFrames.getBowlerName(),
                bowlerFrames.getScore());
    }

}
