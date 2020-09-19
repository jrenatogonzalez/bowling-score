package com.rgonzalez.bowling.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.rgonzalez.bowling.test.TestConstants.MUST_BE_POSITIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class DefaultFrameTest {
    private DefaultFrame defaultFrame;

    @BeforeEach
    void setup() {
        defaultFrame = new DefaultFrame();
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideValidKnockedDownPinsNumbersAddRollTestData")
    void addRoll_WhenValidKnockedPinsNumber_ShouldReturnAddedKnockedDownPin(
            List<Integer> previousRolls,
            Integer rollToTest,
            Optional<Integer> expected) {
        previousRolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        Optional<Integer> result = defaultFrame.addRoll(Chance.with(rollToTest));
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideNotValidKnockedDownPinsNumbersAddRollTestData")
    void addRoll_WhenNotValidKnockedPinsNumber_ShouldReturnAddedKnockedDownPin(
            List<Integer> previousRolls,
            Integer rollToTest) {
        previousRolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        assertThatThrownBy(() -> defaultFrame.addRoll(Chance.with(rollToTest)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MUST_BE_POSITIVE);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideValidKnockedDownPinsNumbersAddExtraRollTestData")
    void addExtraRoll_WhenValidKnockedPinsNumber_ShouldReturnAddedKnockedDownPin(
            List<Integer> rolls,
            List<Integer> previousExtraRolls,
            Integer extraRollToTest,
            Optional<Integer> expected) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        previousExtraRolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addExtraRoll);
        Optional<Integer> result = defaultFrame.addExtraRoll(Chance.with(extraRollToTest));
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideNotValidKnockedDownPinsNumbersAddExtraRollTestData")
    void addExtraRoll_WhenNotValidKnockedPinsNumber_ShouldReturnAddedKnockedDownPin(
            List<Integer> rolls,
            List<Integer> previousExtraRolls,
            Integer extraRollToTest) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        previousExtraRolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addExtraRoll);
        assertThatThrownBy(() -> defaultFrame.addExtraRoll(Chance.with(extraRollToTest)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MUST_BE_POSITIVE);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideScoreTestData")
    void getScore_ShouldReturnSumOfKnockedDownPins(List<Integer> rolls, List<Integer> extraRolls,
                                                   Integer expectedScore) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        extraRolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addExtraRoll);
        Integer result = defaultFrame.getScore();
        assertThat(result).isEqualTo(expectedScore);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideIsFinishedTestData")
    void isFinished_ShouldReturnIfFinished(List<Integer> rolls,
                                           List<Integer> extraRolls,
                                           boolean expected) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        extraRolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addExtraRoll);
        boolean result = defaultFrame.isFinished();
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideStrikeTestData")
    void isStrike_WhenAllPinsAreKnockedDownOnFirstRoll_ShouldReturnTrue(List<Integer> rolls, boolean expected) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        boolean result = defaultFrame.isStrike();
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideSpareTestData")
    void isSpare_WhenAllPinsAreKnockedDownMoreThanOneRoll_ShouldReturnTrue(List<Integer> rolls, boolean expected) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        boolean result = defaultFrame.isSpare();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getCumulativeScore_ShouldReturnPreviousFrameScorePlusFrameScore() {
        defaultFrame.addRoll(Chance.with(4));
        defaultFrame.addRoll(Chance.with(5));
        defaultFrame.setPreviousFrameScore(20);
        Integer result = defaultFrame.getCumulativeScore();
        assertThat(result).isEqualTo(29);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultRollHandlerTestData#provideIsFinishedTestData")
    void isRollsFinished_WhenMaxPinsReached_ShouldReturnTrue(List<Integer> rolls,
                                                             boolean expected) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        boolean result = defaultFrame.isRollsFinished();
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideVariableSizeRollKnockedDownPins")
    void getRolls(List<Integer> rolls) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        Stream<Chance> result = defaultFrame.getRolls();
        testRolls(rolls, result);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultFrameTestData#provideVariableSizeExtraRollKnockedDownPins")
    void getExtraRolls(List<Integer> rolls, List<Integer> extraRolls) {
        rolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addRoll);
        extraRolls.stream()
                .map(Chance::with)
                .forEach(defaultFrame::addExtraRoll);
        Stream<Chance> result = defaultFrame.getExtraRolls();
        testRolls(extraRolls, result);
    }

    private void testRolls(List<Integer> rolls, Stream<Chance> result) {
        List<Chance> resultList = result.collect(Collectors.toList());
        if (!resultList.isEmpty()) {
            IntStream.range(0, rolls.size())
                    .forEach(i -> Assertions.assertThat(resultList.get(i))
                            .isEqualTo(Chance.with(rolls.get(i))));
        }
        Assertions.assertThat(resultList.size()).isEqualTo(rolls.size());
    }

}
