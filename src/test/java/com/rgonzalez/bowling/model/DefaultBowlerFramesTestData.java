package com.rgonzalez.bowling.model;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class DefaultBowlerFramesTestData {

    private static BowlFrameConstraints createShortFramesBowlConstraints() {
        BowlFrameConstraints constraints = new BowlFrameConstraints(
                BowlFrameConstraints.MAX_PINS,
                BowlFrameConstraints.MAX_ROLLS,
                BowlFrameConstraints.MAX_EXTRA_ROLLS_IF_SPARE,
                BowlFrameConstraints.MAX_EXTRA_ROLLS_IF_STRIKE,
                2
        );
        return constraints;
    }

    public static DefaultBowlerFrames createShortBowlerFrames() {
        return new DefaultBowlerFrames("Gerard",
                createShortFramesBowlConstraints());
    }

    public static Stream<Arguments> provideRollsBeforeFinish() {
        return Stream.of(
                Arguments.of(Arrays.asList(2, 3), 5),
                Arguments.of(Collections.singletonList(3), 5),
                Arguments.of(Collections.emptyList(), 6),
                Arguments.of(Arrays.asList(10, 2), 7),
                Arguments.of(Arrays.asList(2, 8, 10), 6)
        );
    }

    public static Stream<Arguments> provideRollsAfterFinish() {
        return Stream.of(
                Arguments.of(Arrays.asList(2, 3, 5, 2), 5),
                Arguments.of(Arrays.asList(10, 10, 10, 10, 10, 10), 7),
                Arguments.of(Arrays.asList(2, 8, 10, 9, 1, 5), 6)
        );
    }

    public static Stream<Arguments> provideFinishedRollsForBowlerFrames() {
        return Stream.of(
                Arguments.of(Arrays.asList(2, 3, 2, 5)),
                Arguments.of(Arrays.asList(10, 3, 6)),
                Arguments.of(Arrays.asList(10, 3, 7, 5, 2)),
                Arguments.of(Arrays.asList(10, 10, 10, 8, 2, 10)),
                Arguments.of(Arrays.asList(10, 3, 2, 10, 10, 10))
        );
    }

    public static Stream<Arguments> provideNotFinishedRollsForBowlerFrames() {
        return Stream.of(
                Arguments.of(Collections.singletonList(4)),
                Arguments.of(Arrays.asList(10, 3)),
                Arguments.of(Arrays.asList(2, 8)),
                Arguments.of(Arrays.asList(3, 7)),
                Arguments.of(Collections.emptyList())
        );
    }

    public static Stream<Arguments> provideRollsForScore() {
        return Stream.of(
                Arguments.of(Arrays.asList(10, 3, 6), 28),
                Arguments.of(Arrays.asList(2, 3, 2, 5), 12),
                Arguments.of(Arrays.asList(10, 3, 7, 5), 35),
                Arguments.of(Arrays.asList(10, 10, 10, 8), 58),
                Arguments.of(Arrays.asList(10, 3, 10, 10, 10, 10), 16),
                Arguments.of(Collections.singletonList(4), 4),
                Arguments.of(Arrays.asList(0, 2), 2),
                Arguments.of(Collections.singletonList(0), 0),
                Arguments.of(Arrays.asList(10, 3), 16),
                Arguments.of(Arrays.asList(2, 8), 10),
                Arguments.of(Arrays.asList(3, 7), 10),
                Arguments.of(Collections.emptyList(), 0)
        );
    }
}
