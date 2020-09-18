package com.rgonzalez.bowling.model;

import java.util.stream.Stream;

public class DefaultBowlingScorePrinter implements BowlingScorePrinter {

    @Override
    public Stream<String> printScore(Stream<BowlerFrames> bowlerFramesStream) {
        return bowlerFramesStream.map(this::getFinalScore);
    }

    private String getFinalScore(BowlerFrames bowlerFrames) {
        StringBuilder sb = new StringBuilder(bowlerFrames.getBowlerName());
        sb.append(":\t");
        bowlerFrames.getFrames()
                .forEach(frame ->
                        sb.append(frame.getCumulativeScore())
                                .append("\t"));
        return sb.toString();
    }

}
