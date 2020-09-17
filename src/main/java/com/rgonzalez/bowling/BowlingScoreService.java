package com.rgonzalez.bowling;

import java.io.BufferedReader;
import java.util.stream.Stream;

public interface BowlingScoreService {

    Stream<String> getScoreForBowlingGame(BufferedReader bowlingGameReader);

}
