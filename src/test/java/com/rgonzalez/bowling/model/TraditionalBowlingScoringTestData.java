package com.rgonzalez.bowling.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TraditionalBowlingScoringTestData {

    public static Stream<Map<String, List<Integer>>> provideGetBowlerFramesTestData() {
        Map<String, List<Integer>> bowlerRolls = new HashMap<>();
        bowlerRolls.put("John", Arrays.asList(5, 4, 10, 10, 10, 4));
        bowlerRolls.put("Ana", Arrays.asList(2, 4, 1, 8, 6, 9));
        bowlerRolls.put("Tim", Arrays.asList(5, 7, 3, 1, 10, 8));
        return Stream.of(bowlerRolls);
    }
}
