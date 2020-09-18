package com.rgonzalez.bowling;

import com.rgonzalez.bowling.common.FileUtils;
import com.rgonzalez.bowling.model.BowlingScorePrinter;
import com.rgonzalez.bowling.model.BowlingScoring;
import com.rgonzalez.bowling.model.DefaultBowlingScorePrinter;
import com.rgonzalez.bowling.model.TraditionalBowlingScoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class BowlingScoreApp {
    public static void main(String[] args) {
        BowlingScoreApp bowlingScoreApp = new BowlingScoreApp();
        try {
            if (args != null) {
                FileUtils fileUtils = new FileUtils();
                System.out.print(bowlingScoreApp.getScore(fileUtils.getReader(args)));
            } else {
                System.out.println("Input file must be specified.");
            }
        } catch (IOException e) {
            System.out.println(String.format("IOError trying to open then file: %s", e.getMessage()));
        }
    }

    public String getScore(BufferedReader bowlingGameReader) {
        BowlingScoring bowlingScoring = new TraditionalBowlingScoring();
        BowlingScorePrinter bowlingScorePrinter = new DefaultBowlingScorePrinter();
        BowlingScoreService bowlingScoreService = new DefaultBowlingScoreService(bowlingScoring, bowlingScorePrinter);
        return bowlingScoreService.getScoreForBowlingGame(bowlingGameReader)
                .collect(Collectors.joining());
    }

}
