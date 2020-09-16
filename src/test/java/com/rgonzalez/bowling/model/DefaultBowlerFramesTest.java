package com.rgonzalez.bowling.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;

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
    void addRoll_WhenRollBeforeFinish_ShouldReturnTestRoll(List<Integer> previousRolls, Integer testRoll) {
        DefaultBowlerFrames shortBowlerFrames = createShortBowlerFrames();
        previousRolls.forEach(shortBowlerFrames::addRoll);
        Optional<Integer> result = shortBowlerFrames.addRoll(testRoll);
        assertThat(result).isEqualTo(Optional.of(testRoll));
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultBowlerFramesTestData#provideRollsAfterFinish")
    void addRoll_WhenRollAfterFinish_ShouldReturnEmptyOptional(List<Integer> previousRolls, Integer testRoll) {
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

}
