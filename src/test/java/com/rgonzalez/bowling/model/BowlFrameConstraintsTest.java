package com.rgonzalez.bowling.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BowlFrameConstraintsTest {

    @Test
    void bowlFrameConstraints_WhenNoArgsConstructor_ShouldReturnInstanceWithDefaultConstraints() {
        BowlFrameConstraints bowlFrameConstraints = new BowlFrameConstraints();
        assertThat(bowlFrameConstraints.getMaxPins())
                .isEqualTo(BowlFrameConstraints.MAX_PINS);
        assertThat(bowlFrameConstraints.getMaxRolls())
                .isEqualTo(BowlFrameConstraints.MAX_ROLLS);
        assertThat(bowlFrameConstraints.getMaxExtraRollsIfSpare())
                .isEqualTo(BowlFrameConstraints.MAX_EXTRA_ROLLS_IF_SPARE);
        assertThat(bowlFrameConstraints.getMaxExtraRollsIfStrike())
                .isEqualTo(BowlFrameConstraints.MAX_EXTRA_ROLLS_IF_STRIKE);
    }

    @Test
    void bowlFrameConstraints_WhenAllArgsConstructor_ShouldReturnInstanceWithCustomConstraints() {
        Integer customMaxPins = 12;
        Integer customMaxRolls = 3;
        Integer customMaxExtraRollsIfSpare = 2;
        Integer customMaxExtraRollsIfStrike = 3;
        BowlFrameConstraints bowlFrameConstraints = new BowlFrameConstraints(
                customMaxPins,
                customMaxRolls,
                customMaxExtraRollsIfSpare,
                customMaxExtraRollsIfStrike);
        assertThat(bowlFrameConstraints.getMaxPins()).isEqualTo(customMaxPins);
        assertThat(bowlFrameConstraints.getMaxRolls()).isEqualTo(customMaxRolls);
        assertThat(bowlFrameConstraints.getMaxExtraRollsIfSpare()).isEqualTo(customMaxExtraRollsIfSpare);
        assertThat(bowlFrameConstraints.getMaxExtraRollsIfStrike()).isEqualTo(customMaxExtraRollsIfStrike);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMaxPinsRollsIntegers")
    void bowlFrameConstraints_WhenInvalidMaxPins_ShouldThrowsException(Integer customMaxPins,
                                                                       Class runtimeExceptionClass) {
        Integer customMaxRolls = 3;
        Integer customMaxExtraRollsIfSpare = 2;
        Integer customMaxExtraRollsIfStrike = 3;
        assertThatThrownBy(() -> new BowlFrameConstraints(
                customMaxPins,
                customMaxRolls,
                customMaxExtraRollsIfSpare,
                customMaxExtraRollsIfStrike))
                .isInstanceOf(runtimeExceptionClass);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMaxPinsRollsIntegers")
    void bowlFrameConstraints_WhenInvalidMaxRolls_ShouldThrowsException(Integer customMaxRolls,
                                                                        Class runtimeExceptionClass) {
        Integer customMaxPins = 13;
        Integer customMaxExtraRollsIfSpare = 2;
        Integer customMaxExtraRollsIfStrike = 3;
        assertThatThrownBy(() -> new BowlFrameConstraints(
                customMaxPins,
                customMaxRolls,
                customMaxExtraRollsIfSpare,
                customMaxExtraRollsIfStrike))
                .isInstanceOf(runtimeExceptionClass);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMaxExtraRollsIntegers")
    void bowlFrameConstraints_WhenInvalidMaxExtraRollsIfSpare_ShouldThrowsException(Integer customMaxExtraRollsIfSpare,
                                                                                    Class runtimeExceptionClass) {
        Integer customMaxPins = 3;
        Integer customMaxRolls = 3;
        Integer customMaxExtraRollsIfStrike = 4;
        assertThatThrownBy(() -> new BowlFrameConstraints(
                customMaxPins,
                customMaxRolls,
                customMaxExtraRollsIfSpare,
                customMaxExtraRollsIfStrike))
                .isInstanceOf(runtimeExceptionClass);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMaxExtraRollsIntegers")
    void bowlFrameConstraints_WhenInvalidMaxExtraRollsIfStrike_ShouldThrowsException(Integer customMaxExtraRollsIfStrike,
                                                                                     Class runtimeExceptionClass) {
        Integer customMaxPins = 3;
        Integer customMaxRolls = 3;
        Integer customMaxExtraRollsIfSpare = 3;
        assertThatThrownBy(() -> new BowlFrameConstraints(
                customMaxPins,
                customMaxRolls,
                customMaxExtraRollsIfSpare,
                customMaxExtraRollsIfStrike))
                .isInstanceOf(runtimeExceptionClass);
    }


    private static Stream<Arguments> provideInvalidMaxPinsRollsIntegers() {
        return Stream.of(
                Arguments.of(0, IllegalArgumentException.class),
                Arguments.of(-1, IllegalArgumentException.class),
                Arguments.of(-10, IllegalArgumentException.class),
                Arguments.of(null, NullPointerException.class)
        );
    }

    private static Stream<Arguments> provideInvalidMaxExtraRollsIntegers() {
        return Stream.of(
                Arguments.of(-1, IllegalArgumentException.class),
                Arguments.of(-10, IllegalArgumentException.class),
                Arguments.of(null, NullPointerException.class)
        );
    }
}
