package com.rgonzalez.bowling;

import com.rgonzalez.bowling.model.BowlingScorePrinter;
import com.rgonzalez.bowling.model.BowlingScoring;
import com.rgonzalez.bowling.model.DefaultBowlingScorePrinter;
import com.rgonzalez.bowling.model.TraditionalBowlingScoring;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BowlingScoreApp {

    public static void main(String[] args) {
        BowlingScoreApp bowlingScoreApp = new BowlingScoreApp();
        System.out.print(
                bowlingScoreApp.getScore(
                        bowlingScoreApp.getReader(args)
                )
        );
    }

    public String getScore(BufferedReader bowlingGameReader) {
        BowlingScoring bowlingScoring = new TraditionalBowlingScoring();
        BowlingScorePrinter bowlingScorePrinter = new DefaultBowlingScorePrinter();
        BowlingScoreService bowlingScoreService = new DefaultBowlingScoreService(bowlingScoring, bowlingScorePrinter);
        return bowlingScoreService.getScoreForBowlingGame(bowlingGameReader)
                .collect(Collectors.joining());
    }

    private String getFileName(String[] args) {
        return Arrays.stream(args)
                .collect(Collectors.joining(" "));
    }

    private BufferedReader getReader(String[] args) {
        String filename = getFileName(args);
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filename))) {
            return bufferedReader;
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File '%s' not found.", filename));
            return null;
        } catch (IOException e) {
            System.out.println(String.format("IOError trying to open file: %s", filename));
            return null;
        }
    }

}
