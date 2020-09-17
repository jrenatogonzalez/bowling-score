package com.rgonzalez.bowling.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TraditionalBowlingScoringTest {
    private TraditionalBowlingScoring traditionalBowlingScoring;

    @BeforeEach
    void setup() {
        traditionalBowlingScoring = new TraditionalBowlingScoring();
    }

    @Test
    void addRoll_WhenNewBowlerRoll_ShouldAddNewBowlerFrame() {
        long framesCountBeforeAddRoll = traditionalBowlingScoring.getBowlerFrames().count();
        Optional<Integer> result = traditionalBowlingScoring.addRoll("John", 8);
        long framesCountAfterAddRoll = traditionalBowlingScoring.getBowlerFrames().count();
        assertThat(framesCountBeforeAddRoll).isEqualTo(0);
        assertThat(framesCountAfterAddRoll).isEqualTo(1);
        assertThat(result).isEqualTo(Optional.of(8));
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.TraditionalBowlingScoringTestData#provideGetBowlerFramesTestData")
    void getBowlerFrames_ShouldReturnAllBowlerFrames(Map<String, List<Integer>> bowlerRolls) {
        addBowlerRolls(bowlerRolls);
        Stream<BowlerFrames> result = traditionalBowlingScoring.getBowlerFrames();
        assertThat(result.count())
                .isEqualTo(bowlerRolls.keySet()
                        .size());
        Stream<BowlerFrames> resultToCheckNames = traditionalBowlingScoring.getBowlerFrames();
        assertThat(resultToCheckNames)
                .allSatisfy(bowlerFrame -> bowlerRolls
                        .containsKey(bowlerFrame.getBowlerName()));
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.TraditionalBowlingScoringTestData#provideGetBowlerFramesTestData")
    void isFinished_WhenNotAllBowlerFramesFinished_ShouldReturnFalse(Map<String, List<Integer>> bowlerRolls) {
        addBowlerRolls(bowlerRolls);
        boolean result = traditionalBowlingScoring.isFinished();
        assertThat(result).isFalse();
    }

    private void addBowlerRolls(Map<String, List<Integer>> bowlerRolls) {
        bowlerRolls.keySet()
                .forEach(name -> bowlerRolls.get(name)
                        .forEach(roll -> traditionalBowlingScoring.addRoll(name, roll)));
    }
}
