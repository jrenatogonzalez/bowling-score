package com.rgonzalez.bowling.model;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.rgonzalez.bowling.model.BowlingFileParserTestData.getSomeValidResultsTestData;
import static com.rgonzalez.bowling.model.BowlingFileParserTestData.getValidResultsTestData;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BowlingFileParserTest {

    @Test
    void getResults_WhenAllValidStringResults_ShoulReturnSameAsBowlerResults() {
        BowlingFileParser bowlingFileParser = new BowlingFileParser(getValidResultsTestData());
        Stream<BowlerResults> result = bowlingFileParser.getResults();
        assertThat(result.count()).isEqualTo(35);
    }

    @Test
    void getResults_WhenSomeNotValidStringResults_ShoulReturnOnlyValidBowlerResults() {
        BowlingFileParser bowlingFileParser = new BowlingFileParser(getSomeValidResultsTestData());
        Stream<BowlerResults> result = bowlingFileParser.getResults();
        assertThat(result.count()).isEqualTo(28);
    }

}
