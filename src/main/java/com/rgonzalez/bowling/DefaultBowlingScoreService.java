package com.rgonzalez.bowling;

import com.rgonzalez.bowling.model.BowlingFileParser;
import com.rgonzalez.bowling.model.BowlingScorePrinter;
import com.rgonzalez.bowling.model.BowlingScoring;

import java.io.BufferedReader;
import java.util.stream.Stream;

public class DefaultBowlingScoreService implements BowlingScoreService {
    private final BowlingScoring bowlingScoring;
    private final BowlingScorePrinter bowlingScorePrinter;

    public DefaultBowlingScoreService(BowlingScoring bowlingScoring,
                                      BowlingScorePrinter bowlingScorePrinter) {
        this.bowlingScoring = bowlingScoring;
        this.bowlingScorePrinter = bowlingScorePrinter;
    }

    @Override
    public Stream<String> getScoreForBowlingGame(BufferedReader bowlingGameReader) {
        BowlingFileParser bowlingFileParser = new BowlingFileParser(bowlingGameReader);
        bowlingFileParser.getResults()
                .forEach(bowlingResults ->
                        bowlingScoring.addRoll(bowlingResults.getBowlerName(),
                                bowlingResults.getResult()));
        return bowlingScorePrinter.printScore(bowlingScoring.getBowlerFrames());
    }
}
