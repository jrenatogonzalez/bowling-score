package com.rgonzalez.bowling.model;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DefaultFrameTestData {
    public static Stream<Arguments> provideScoreTestData() {
        return Stream.of(
                Arguments.of(Collections.emptyList(), Collections.emptyList(), 0),
                Arguments.of(Arrays.asList(0, 0), Collections.emptyList(), 0),
                Arguments.of(Arrays.asList(0, 6), Collections.emptyList(), 6),
                Arguments.of(Arrays.asList(5, 0), Collections.emptyList(), 5),
                Arguments.of(Arrays.asList(5, 2), Collections.emptyList(), 7),
                Arguments.of(Arrays.asList(0, 10), Collections.emptyList(), 10),
                Arguments.of(Arrays.asList(0, 10), Collections.singletonList(0), 10),
                Arguments.of(Arrays.asList(0, 10), Collections.singletonList(5), 15),
                Arguments.of(Arrays.asList(0, 10), Collections.singletonList(10), 20),
                Arguments.of(Collections.singletonList(10), Collections.emptyList(), 10),
                Arguments.of(Collections.singletonList(10), Arrays.asList(0, 0), 10),
                Arguments.of(Collections.singletonList(10), Arrays.asList(0, 2), 12),
                Arguments.of(Collections.singletonList(10), Arrays.asList(8, 2), 20),
                Arguments.of(Collections.singletonList(10), Arrays.asList(10, 10), 30)
        );
    }

    public static Stream<Arguments> provideValidKnockedDownPinsNumbersAddRollTestData() {
        return Stream.of(
                Arguments.of(Collections.emptyList(), 5, Optional.of(5)),
                Arguments.of(Collections.emptyList(), 8, Optional.of(8)),
                Arguments.of(Collections.singletonList(0), 6, Optional.of(6)),
                Arguments.of(Collections.singletonList(0), 12, Optional.empty()),
                Arguments.of(Collections.singletonList(5), 4, Optional.of(4)),
                Arguments.of(Collections.singletonList(5), 5, Optional.of(5)),
                Arguments.of(Collections.emptyList(), 11, Optional.empty()),
                Arguments.of(Collections.singletonList(5), 6, Optional.empty())
        );
    }

    public static Stream<Arguments> provideNotValidKnockedDownPinsNumbersAddRollTestData() {
        return Stream.of(
                Arguments.of(Collections.emptyList(), -3),
                Arguments.of(Collections.singletonList(5), -9),
                Arguments.of(Collections.singletonList(5), -100)
        );
    }

    public static Stream<Arguments> provideValidKnockedDownPinsNumbersAddExtraRollTestData() {
        return Stream.of(
                Arguments.of(Arrays.asList(2, 8), Collections.emptyList(), 5, Optional.of(5)),
                Arguments.of(Arrays.asList(0, 10), Collections.emptyList(), 6, Optional.of(6)),
                Arguments.of(Arrays.asList(0, 10), Collections.singletonList(4), 6, Optional.empty()),
                Arguments.of(Collections.singletonList(10), Collections.emptyList(), 4, Optional.of(4)),
                Arguments.of(Collections.singletonList(10), Collections.emptyList(), 12, Optional.empty()),
                Arguments.of(Collections.singletonList(10), Collections.singletonList(5), 4, Optional.of(4)),
                Arguments.of(Collections.singletonList(10), Collections.singletonList(5), 6, Optional.of(6)),
                Arguments.of(Collections.singletonList(10), Collections.singletonList(10), 11, Optional.empty()),
                Arguments.of(Collections.singletonList(10), Collections.singletonList(0), 3, Optional.of(3))
        );
    }

    public static Stream<Arguments> provideNotValidKnockedDownPinsNumbersAddExtraRollTestData() {
        return Stream.of(
                Arguments.of(Arrays.asList(2, 8), Collections.emptyList(), -5),
                Arguments.of(Arrays.asList(0, 10), Collections.emptyList(), -6),
                Arguments.of(Collections.singletonList(10), Collections.emptyList(), -4),
                Arguments.of(Collections.singletonList(10), Collections.singletonList(5), -4),
                Arguments.of(Collections.singletonList(10), Collections.singletonList(10), -11)
        );
    }

    public static Stream<Arguments> provideStrikeTestData() {
        return Stream.of(
                Arguments.of(Collections.singletonList(10), true),
                Arguments.of(Arrays.asList(6, 4), false),
                Arguments.of(Arrays.asList(10, 0), true)
        );
    }

    public static Stream<Arguments> provideSpareTestData() {
        return Stream.of(
                Arguments.of(Collections.singletonList(10), false),
                Arguments.of(Arrays.asList(5, 5), true),
                Arguments.of(Arrays.asList(7, 3), true),
                Arguments.of(Arrays.asList(0, 10), true),
                Arguments.of(Arrays.asList(10, 0), false)
        );
    }

    public static Stream<List<Integer>> provideVariableSizeRollKnockedDownPins() {
        return Stream.of(
                Arrays.asList(6, 1),
                Collections.singletonList(4),
                Collections.emptyList(),
                Arrays.asList(7, 3),
                Arrays.asList(10, 0)
        );
    }

    public static Stream<Arguments> provideVariableSizeExtraRollKnockedDownPins() {
        return Stream.of(
                Arguments.of(Arrays.asList(6, 4), Collections.singletonList(8)),
                Arguments.of(Collections.singletonList(10), Arrays.asList(5, 4)),
                Arguments.of(Collections.singletonList(10), Collections.singletonList(1)),
                Arguments.of(Arrays.asList(7, 3), Collections.singletonList(6)),
                Arguments.of(Arrays.asList(10, 0), Collections.singletonList(10))
        );
    }

    public static Stream<Arguments> provideIsFinishedTestData() {
        return Stream.of(
                Arguments.of(Collections.singletonList(5), Collections.emptyList(), false),
                Arguments.of(Arrays.asList(5, 2), Collections.emptyList(), true),
                Arguments.of(Arrays.asList(2, 8), Collections.emptyList(), false),
                Arguments.of(Arrays.asList(7, 3), Collections.singletonList(4), true),
                Arguments.of(Collections.singletonList(10), Collections.emptyList(), false),
                Arguments.of(Collections.singletonList(10), Arrays.asList(2, 3), true),
                Arguments.of(Collections.singletonList(10), Collections.singletonList(3), false)
        );
    }

}
