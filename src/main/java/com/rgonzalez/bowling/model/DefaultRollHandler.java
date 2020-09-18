package com.rgonzalez.bowling.model;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.rgonzalez.bowling.common.Validations.checkIfIsPositive;

@Log
public class DefaultRollHandler implements RollHandler {
    private final Integer maxRolls;
    private final Integer maxPins;
    private boolean maxPinsPerRoll = false;
    private final List<Integer> rolls = new ArrayList<>();


    public DefaultRollHandler(Integer maxRolls, Integer maxPins, boolean maxPinsPerRoll) {
        this.maxRolls = maxRolls;
        this.maxPins = maxPins;
        this.maxPinsPerRoll = maxPinsPerRoll;
    }

    public DefaultRollHandler(Integer maxRolls, Integer maxPins) {
        this.maxRolls = maxRolls;
        this.maxPins = maxPins;
        this.maxPinsPerRoll = false;
    }

    @Override
    public Optional<Integer> add(int knockedDownPins) {
        checkIfIsPositive(knockedDownPins, "knockedDownPins");
        if (willExceedMaxRolls()) {
            log.fine("Request to add a new roll was rejected, it will exceed max rolls.");
            return Optional.empty();
        }
        if (willExceedRollPinCapacity(knockedDownPins)) {
            log.fine(String.format("Request to add %d knockedDownPins was rejected, it will exceed max pins per roll.",
                    knockedDownPins));
            return Optional.empty();
        }
        if (willExceedCapacity(knockedDownPins)) {
            log.fine(String.format("Request to add %d knockedDownPins was rejected, it will exceed max pins.",
                    knockedDownPins));
            return Optional.empty();
        }
        rolls.add(knockedDownPins);
        return Optional.of(knockedDownPins);
    }

    @Override
    public Optional<Integer> getKnockedDownPins(int rollIndex) {
        if (rollIndex < 0 || rollIndex > (maxRolls - 1)) {
            throw new IllegalArgumentException("Invalid rollIndex argument.");
        }
        return (rollIndex > (rolls.size() - 1)) ?
                Optional.empty() : Optional.of(rolls.get(rollIndex));
    }

    @Override
    public Integer getTotalKnockedDownPins() {
        return rolls.stream()
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public Stream<Integer> getRolls() {
        return rolls.stream();
    }

    @Override
    public Integer size() {
        return rolls.size();
    }

    @Override
    public boolean isFinished() {
        return getTotalKnockedDownPins().equals(maxPins) ||
                size() == maxRolls;
    }

    @Override
    public Integer pinCapacity() {
        return maxPinsPerRoll ? maxPins * maxRolls : maxPins;
    }

    private boolean willExceedCapacity(int knockedDownPins) {
        return (getTotalKnockedDownPins() + knockedDownPins) > pinCapacity();
    }

    private boolean willExceedRollPinCapacity(int knockedDownPins) {
        return (knockedDownPins > maxPins);
    }

    private boolean willExceedMaxRolls() {
        return rolls.size() == maxRolls;
    }
}
