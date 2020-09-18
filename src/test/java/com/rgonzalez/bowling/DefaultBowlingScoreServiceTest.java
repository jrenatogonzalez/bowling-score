package com.rgonzalez.bowling;

import com.rgonzalez.bowling.model.DefaultBowlingScorePrinter;
import com.rgonzalez.bowling.model.TraditionalBowlingScoring;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.rgonzalez.bowling.model.BowlingFileParserTestData.getValidResultsTestData;

class DefaultBowlingScoreServiceTest {

    @Test
    void getScoreForBowlingGame() {
        BowlingScoreService bowlingScoreService = new DefaultBowlingScoreService(
                new TraditionalBowlingScoring(),
                new DefaultBowlingScorePrinter());
        Stream<String> result = bowlingScoreService.getScoreForBowlingGame(getValidResultsTestData());
        result.forEach(System.out::print);
    }
}
