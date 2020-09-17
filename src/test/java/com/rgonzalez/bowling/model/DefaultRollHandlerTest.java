package com.rgonzalez.bowling.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.rgonzalez.bowling.test.TestConstants.INVALID_ROLLINDEX_ARGUMENT;
import static com.rgonzalez.bowling.test.TestConstants.MUST_BE_POSITIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultRollHandlerTest {
    private DefaultRollHandler defaultRollHandler;

    @BeforeEach
    void setup() {
        defaultRollHandler = new DefaultRollHandler(BowlFrameConstraints.MAX_ROLLS,
                BowlFrameConstraints.MAX_PINS);
    }

    @Test
    void add_WhenNegativePins_ShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> defaultRollHandler.add(-4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MUST_BE_POSITIVE);
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 15, 100})
    void add_WhenWillExceedMaxPinsOnFirstRoll_ShouldReturnEmptyOptional(int knockedDownPins) {
        Optional<Integer> result = defaultRollHandler.add(knockedDownPins);
        assertThat(result).isEmpty();
    }

    @Test
    void add_WhenWillExceedMaxPinsOnSecondRoll_ShouldReturnEmptyOptional() {
        defaultRollHandler.add(6);
        Optional<Integer> result = defaultRollHandler.add(5);
        assertThat(result).isEmpty();
    }

    @Test
    void add_WhenWillExceedMaxRolls_ShouldReturnEmptyOptional() {
        defaultRollHandler.add(4);
        defaultRollHandler.add(2);
        Optional<Integer> result = defaultRollHandler.add(3);
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10})
    void add_WhenValidPins_ShouldReturnOptionalInteger(int knockedDownPins) {
        Optional<Integer> result = defaultRollHandler.add(knockedDownPins);
        assertThat(result).isPresent()
                .hasValue(knockedDownPins);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultRollHandlerTestData#provideFixedSizeRollKnockedDownPins")
    void getKnockedDownPins_WhenRollExists_ShouldReturnKnockedDownPinsInRoll(List<Integer> rolls) {
        rolls.forEach(defaultRollHandler::add);
        IntStream.range(0, rolls.size())
                .forEach(i -> assertThat(defaultRollHandler.getKnockedDownPins(i))
                        .isPresent()
                        .hasValue(rolls.get(i)));
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultRollHandlerTestData#provideVariableSizeRollKnockedDownPins")
    void getKnockedDownPins_WhenRollNotExists_ShouldReturnEmptyOptional(List<Integer> rolls) {
        rolls.forEach(defaultRollHandler::add);
        Optional<Integer> result = defaultRollHandler.getKnockedDownPins(rolls.size());
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 12})
    void getKnockedDownPins_WhenInvalidRollIndex_ShoudReturnInvalidArgumentException(int rollIndex) {
        assertThatThrownBy(() -> defaultRollHandler.getKnockedDownPins(rollIndex))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_ROLLINDEX_ARGUMENT);
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultRollHandlerTestData#provideVariableSizeRollKnockedDownPins2")
    void getTotalKnockedDownPins_ShouldReturnSumKnockedDownPins(List<Integer> rolls) {
        rolls.forEach(defaultRollHandler::add);
        Integer result = defaultRollHandler.getTotalKnockedDownPins();
        assertThat(result).isEqualTo(rolls.stream()
                .reduce(Integer::sum)
                .orElse(0));
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultRollHandlerTestData#provideVariableSizeRollKnockedDownPins2")
    void getRolls_ShouldReturnStreamOfInteger(List<Integer> rolls) {
        rolls.forEach(defaultRollHandler::add);
        Stream<Integer> result = defaultRollHandler.getRolls();
        List<Integer> resultList = result.collect(Collectors.toList());
        IntStream.range(0, rolls.size())
                .forEach(i -> assertThat(resultList.get(i))
                        .isEqualTo(rolls.get(i)));
        assertThat(resultList.size()).isEqualTo(rolls.size());
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultRollHandlerTestData#provideVariableSizeRollKnockedDownPins2")
    void size_ShouldReturnRollHandlerSize(List<Integer> rolls) {
        rolls.forEach(defaultRollHandler::add);
        assertThat(defaultRollHandler.size())
                .isEqualTo(rolls.size());
    }

    @ParameterizedTest
    @MethodSource("com.rgonzalez.bowling.model.DefaultRollHandlerTestData#provideIsFinishedTestData")
    void isFinished_WhenTotalPinFallsIsEqualsToMaxPins_ShouldReturnTrueElseFalse(List<Integer> rolls,
                                                                                 boolean expected) {
        rolls.forEach(defaultRollHandler::add);
        boolean result = defaultRollHandler.isFinished();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void pinCapacity_WhenPinPerRollCapacity_ShouldReturnMaxPinsMultipliedByMaxRolls() {
        DefaultRollHandler extraRollHandler = new DefaultRollHandler(BowlFrameConstraints.MAX_ROLLS,
                BowlFrameConstraints.MAX_PINS,
                true);
        Integer result = extraRollHandler.pinCapacity();
        assertThat(result).isEqualTo(BowlFrameConstraints.MAX_ROLLS * BowlFrameConstraints.MAX_PINS);
    }

    @Test
    void pinCapacity_WhenNoPinPerRollCapacity_ShouldReturnMaxPins() {
        Integer result = defaultRollHandler.pinCapacity();
        assertThat(result).isEqualTo(BowlFrameConstraints.MAX_PINS);
    }

}
