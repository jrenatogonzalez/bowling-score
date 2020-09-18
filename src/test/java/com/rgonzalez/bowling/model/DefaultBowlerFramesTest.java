package com.rgonzalez.bowling.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.rgonzalez.bowling.model.DefaultBowlerFramesTestData.createShortBowlerFrames;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class DefaultBowlerFramesTest {
    private static final String BOWLER_NAME = "John";
    private DefaultBowlerFrames defaultBowlerFrames;

    @BeforeEach
    void setup() {
        defaultBowlerFrames = new DefaultBowlerFrames(BOWLER_NAME);
    }

    @Test
    void getBowlerName_ShouldReturnBowlersName() {
        String result = defaultBowlerFrames.getBowlerName();
        assertThat(result).isEqualTo(BOWLER_NAME);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultBowlerFramesTestData#provideRollsBeforeFinish")
    void addRoll_WhenBowlerFramesWillFinish_ShouldReturnTestRoll(List<Integer> previousRolls, Integer testRoll) {
        DefaultBowlerFrames shortBowlerFrames = createShortBowlerFrames();
        previousRolls.forEach(shortBowlerFrames::addRoll);
        Optional<Integer> result = shortBowlerFrames.addRoll(testRoll);
        assertThat(result).isEqualTo(Optional.of(testRoll));
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultBowlerFramesTestData#provideRollsAfterFinish")
    void addRoll_WhenBowlerFramesFinished_ShouldReturnEmptyOptional(List<Integer> previousRolls, Integer testRoll) {
        DefaultBowlerFrames shortBowlerFrames = createShortBowlerFrames();
        previousRolls.forEach(shortBowlerFrames::addRoll);
        Optional<Integer> result = shortBowlerFrames.addRoll(testRoll);
        assertThat(result).isEqualTo(Optional.empty());
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultBowlerFramesTestData#provideFinishedRollsForBowlerFrames")
    void isFinished_WhenAllFramesFinished_ShouldReturnTrue(List<Integer> rolls) {
        DefaultBowlerFrames shortBowlerFrames = createShortBowlerFrames();
        rolls.forEach(shortBowlerFrames::addRoll);
        boolean result = shortBowlerFrames.isFinished();
        assertThat(result).isTrue();
    }

    @Test
    void isFinished_WhenIsEmpty_ShouldReturnFalse() {
        boolean result = defaultBowlerFrames.isFinished();
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultBowlerFramesTestData#provideNotFinishedRollsForBowlerFrames")
    void isFinished_WhenLastFrameNotFinished_ShouldReturnFalse(List<Integer> rolls) {
        DefaultBowlerFrames shortBowlerFrames = createShortBowlerFrames();
        rolls.forEach(shortBowlerFrames::addRoll);
        boolean result = shortBowlerFrames.isFinished();
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultBowlerFramesTestData#provideRollsForScore")
    void getScore(List<Integer> rolls, Integer expected) {
        DefaultBowlerFrames shortBowlerFrames = createShortBowlerFrames();
        rolls.forEach(shortBowlerFrames::addRoll);
        Integer result = shortBowlerFrames.getScore();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getScore_WhenAllRollsAreZero_ShouldReturnZero() {
        IntStream.range(0, 10)
                .forEach(i -> defaultBowlerFrames.addRoll(0));
        Integer result = defaultBowlerFrames.getScore();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void getScore_WhenAllRollsAreStrike_ShouldReturn300() {
        IntStream.range(0, 12)
                .forEach(i -> defaultBowlerFrames.addRoll(10));
        Integer result = defaultBowlerFrames.getScore();
        assertThat(result).isEqualTo(300);
    }

}
