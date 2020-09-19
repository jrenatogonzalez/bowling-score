package com.rgonzalez.bowling.model;

import java.io.BufferedReader;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.rgonzalez.bowling.common.Validations.checkIfNonNull;

public class BowlingFileParser implements BowlingParser {
    private static final String REGEX_BOWLER_PINS = "([\\w\\s]+)\\t([\\d\\s]+$)";
    private static final String REGEX_BOWLER_FOUL = "([\\w\\s]+)\\t([\\s]*F[\\s]*$)";
    private static final String FAUL_CHARACTER = "F";
    private final Pattern bowlerPinsPattern;
    private final Pattern bowlerFoulPattern;
    private BufferedReader bufferedReader;

    public BowlingFileParser(BufferedReader bufferedReader) {
        checkIfNonNull(bufferedReader, "bufferedReader");
        this.bufferedReader = bufferedReader;
        bowlerPinsPattern = Pattern.compile(REGEX_BOWLER_PINS);
        bowlerFoulPattern = Pattern.compile(REGEX_BOWLER_FOUL);
    }

    @Override
    public Stream<BowlerResults> getResults() {
        return bufferedReader.lines()
                .map(this::parseLine)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Optional<BowlerResults> parseLine(String line) {
        if (isBlankOrEmpty(line)) {
            return Optional.empty();
        } else {
            return createBowlerResults(line);
        }
    }

    private Optional<BowlerResults> createBowlerResults(String line) {
        Optional<BowlerResults> bowlerResults;
        bowlerResults = extractBowlerResults(bowlerPinsPattern, line);
        if (bowlerResults.isEmpty()) {
            bowlerResults = extractBowlerResults(bowlerFoulPattern, line);
        }
        return bowlerResults;
    }

    private Optional<BowlerResults> extractBowlerResults(Pattern bowlerResultsPattern, String line) {
        Optional<BowlerResults> optionalBowlerResults;
        Matcher matcher = bowlerResultsPattern.matcher(line);
        if (matcher.find()) {
            String name = matcher.group(1);
            Optional<Chance> chanceOptional = extractChance(matcher.group(2));
            if (chanceOptional.isPresent()) {
                BowlerResults bowlerResults = new BowlerResults(name, chanceOptional.get());
                optionalBowlerResults = Optional.of(bowlerResults);
            } else {
                optionalBowlerResults = Optional.empty();
            }
        } else {
            optionalBowlerResults = Optional.empty();
        }
        return optionalBowlerResults;
    }

    private Optional<Chance> extractChance(String pinsExpression) {
        pinsExpression = pinsExpression.trim();
        if (pinsExpression.equals(FAUL_CHARACTER)) {
            return Optional.of(Chance.withFoul());
        } else {
            try {
                Integer pinFalls = Integer.parseInt(pinsExpression);
                return Optional.of(Chance.with(pinFalls));
            } catch (Throwable t) {
                return Optional.empty();
            }
        }
    }

    private boolean isBlankOrEmpty(String s) {
        return s == null || s.isBlank() || s.isEmpty();
    }
}
