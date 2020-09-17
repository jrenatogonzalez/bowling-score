package com.rgonzalez.bowling.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.rgonzalez.bowling.common.Validations.*;
import static com.rgonzalez.bowling.test.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ValidationsTest {
    private static final String ARGUMENT_NAME = "testValue";

    @Test
    void checkIfNonNull_NullInteger_ShouldThrowNPE() {
        Integer testValue = null;
        assertThatThrownBy(() -> checkIfNonNull(testValue, ARGUMENT_NAME))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(CANNOT_BE_NULL);
    }

    @Test
    void checkIfNonNull_IntegerValue_ShouldReturnIntegerValue() {
        Integer testValue = 45;
        Integer result = checkIfNonNull(testValue, ARGUMENT_NAME);
        assertThat(result).isEqualTo(45);
    }

    @Test
    void checkIfNonNull_NullString_ShouldThrowNPE() {
        String testValue = null;
        assertThatThrownBy(() -> checkIfNonNull(testValue, ARGUMENT_NAME))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(CANNOT_BE_NULL);
    }

    @Test
    void checkIfNonNull_StringValue_ShouldReturnIntegerValue() {
        String testValue = "Test String";
        String result = checkIfNonNull(testValue, ARGUMENT_NAME);
        assertThat(result).isEqualTo(testValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ", "", " "})
    void checkIfNotBlankOrEmpty_WhenIsBlankOrEmpty_ShouldThrowIllegalArgumentException(String testValue) {
        assertThatThrownBy(() -> checkIfNotBlankOrEmpty(testValue, ARGUMENT_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(CANNOT_BE_BLANK_OR_EMPTY);
    }

    @Test
    void checkIfIsPositive_NegativeValue_ShouldThrowIllegalArgumentException() {
        Integer testValue = -3;
        assertThatThrownBy(() -> checkIfIsPositive(testValue, ARGUMENT_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MUST_BE_POSITIVE);
    }

    @Test
    void checkIfIsPositive_PositiveValue_ShouldReturnSameValue() {
        Integer testValue = 10;
        Integer result = checkIfIsPositive(testValue, ARGUMENT_NAME);
        assertThat(result).isEqualTo(10);
    }

    @Test
    void checkIfNonNullAndGreaterThan_NullValue_ShouldThrowsNPE() {
        Integer testValue = null;
        Integer compareToValue = 25;
        assertThatThrownBy(() -> checkIfNonNullAndGreaterThan(testValue, compareToValue, ARGUMENT_NAME))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(CANNOT_BE_NULL);
    }

    @Test
    void checkIfNonNullAndGreaterThan_NullCompareToValue_ShouldThrowNPE() {
        Integer testValue = 34;
        Integer compareToValue = null;
        assertThatThrownBy(() -> checkIfNonNullAndGreaterThan(testValue, compareToValue, ARGUMENT_NAME))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(CANNOT_BE_NULL);
    }

    @Test
    void checkIfNonNullAndGreaterThan_LessThanValue_ShouldThrowIllegalArgumentException() {
        Integer testValue = 14;
        Integer compareToValue = 25;
        assertThatThrownBy(() -> checkIfNonNullAndGreaterThan(testValue, compareToValue, ARGUMENT_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MUST_BE_GREATER);
    }

    @Test
    void checkIfNonNullAndGreaterThan_GreaterThanValue_ShouldReturnSameValue() {
        Integer testValue = 30;
        Integer compareToValue = 25;
        Integer result = checkIfNonNullAndGreaterThan(testValue, compareToValue, ARGUMENT_NAME);
        assertThat(result).isEqualTo(30);
    }
}
