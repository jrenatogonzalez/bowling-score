package com.rgonzalez.bowling.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.rgonzalez.bowling.common.Validations.checkIfNonNull;
import static com.rgonzalez.bowling.common.Validations.checkIfNotBlankOrEmpty;

public class TraditionalBowlingScoring implements BowlingScoring {
    private final Map<String, BowlerFrames> bowlerFramesMap;

    public TraditionalBowlingScoring() {
        bowlerFramesMap = new HashMap<>();
    }

    @Override
    public Optional<Integer> addRoll(String bowlerName, int knockedDownPins) {
        checkIfNonNull(bowlerName, "bowlerName");
        checkIfNotBlankOrEmpty(bowlerName, "bowlerName");
        String name = bowlerName.trim();
        BowlerFrames bowlerFramesToAddRoll;
        bowlerFramesToAddRoll = bowlerFramesMap.getOrDefault(name, createBowlerFrame(name));
        Optional<Integer> result = bowlerFramesToAddRoll.addRoll(knockedDownPins);
        if (!bowlerFramesMap.containsKey(name) && result.isPresent()) {
            bowlerFramesMap.put(name, bowlerFramesToAddRoll);
        }
        return result;
    }

    private BowlerFrames createBowlerFrame(String name) {
        return new DefaultBowlerFrames(name, new BowlFrameConstraints());
    }

    @Override
    public Stream<BowlerFrames> getBowlerFrames() {
        return bowlerFramesMap.values().stream();
    }

    @Override
    public boolean isFinished() {
        return !bowlerFramesMap.isEmpty() && bowlerFramesMap.values()
                .stream()
                .map(BowlerFrames::isFinished)
                .allMatch(b -> b);
    }

}
