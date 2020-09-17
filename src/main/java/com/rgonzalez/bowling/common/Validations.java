package com.rgonzalez.bowling.common;

import java.util.Objects;
import java.util.Optional;

public class Validations {
    private static final String MSG_CANNOT_BE_NULL = "%s cannot be null.";
    private static final String MSG_NOT_GREATER_THAN = "%s must be greater than %d";
    private static final String MSG_MUST_BE_POSITIVE = "%s must be a positive integer.";
    private static final String MSG_CANNOT_BE_BLANK_OR_EMPTY = "%s cannot be blank or empty.";

    public static Integer checkIfNonNull(Integer value, String argumentName) {
        return Objects.requireNonNull(value,
                String.format(MSG_CANNOT_BE_NULL,
                        Optional.ofNullable(argumentName).orElse("")));
    }

    public static String checkIfNonNull(String value, String argumentName) {
        return Objects.requireNonNull(value,
                String.format(MSG_CANNOT_BE_NULL,
                        Optional.ofNullable(argumentName).orElse("")));
    }

    public static Object checkIfNonNull(Object value, String argumentName) {
        return Objects.requireNonNull(value,
                String.format(MSG_CANNOT_BE_NULL,
                        Optional.ofNullable(argumentName).orElse("")));
    }

    public static String checkIfNotBlankOrEmpty(String value, String argumentName) {
        if (value.isBlank() || value.isEmpty()) {
            throw new IllegalArgumentException(String.format(MSG_CANNOT_BE_BLANK_OR_EMPTY, argumentName));
        }
        return value;
    }

    public static Integer checkIfNonNullAndGreaterThan(Integer value, Integer compareToValue, String argumentName) {
        checkIfNonNull(value, argumentName);
        checkIfNonNull(compareToValue, "compareToValue");
        if (value.compareTo(compareToValue) < 1) {
            throw new IllegalArgumentException(String.format(MSG_NOT_GREATER_THAN,
                    Optional.ofNullable(argumentName).orElse(""),
                    compareToValue));
        }
        return value;
    }

    public static Integer checkIfIsPositive(Integer value, String argumentName) {
        checkIfNonNull(value, argumentName);
        if (value.compareTo(0) < 0) {
            throw new IllegalArgumentException(String.format(MSG_MUST_BE_POSITIVE,
                    Optional.ofNullable(argumentName).orElse("")));
        }
        return value;
    }
}
