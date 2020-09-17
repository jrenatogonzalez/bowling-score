package com.rgonzalez.bowling.model;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class DefaultRollHandlerTestData {
    public static Stream<List<Integer>> provideFixedSizeRollKnockedDownPins() {
        return Stream.of(
                Arrays.asList(7, 1),
                Arrays.asList(0, 0),
                Arrays.asList(10, 0),
                Arrays.asList(0, 10)
        );
    }

    public static Stream<List<Integer>> provideVariableSizeRollKnockedDownPins() {
        return Stream.of(
                Collections.singletonList(5),
                Collections.singletonList(0),
                Collections.emptyList()
        );
    }

    public static Stream<List<Integer>> provideVariableSizeRollKnockedDownPins2() {
        return Stream.of(
                Collections.singletonList(5),
                Arrays.asList(7, 2),
                Collections.emptyList(),
                Arrays.asList(8, 2),
                Arrays.asList(10, 0)
        );
    }

    public static Stream<Arguments> provideIsFinishedTestData() {
        return Stream.of(
                Arguments.of(Arrays.asList(4, 2), true),
                Arguments.of(Arrays.asList(6, 4), true),
                Arguments.of(Collections.singletonList(10), true),
                Arguments.of(Collections.singletonList(6), false),
                Arguments.of(Collections.emptyList(), false)
        );
    }

}
