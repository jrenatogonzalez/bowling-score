package com.rgonzalez.bowling.model;

import lombok.extern.java.Log;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Log
public class DefaultFrame implements Frame {
    private final BowlFrameConstraints constraints;
    private final RollHandler rollsHandler;
    private final RollHandler extraRollsHandler;

    public DefaultFrame() {
        this.constraints = new BowlFrameConstraints();
        this.rollsHandler = new DefaultRollHandler(
                constraints.getMaxRolls(),
                constraints.getMaxPins()
        );
        this.extraRollsHandler = new DefaultRollHandler(
                Integer.max(constraints.getMaxExtraRollsIfStrike(), constraints.getMaxExtraRollsIfSpare()),
                constraints.getMaxPins(),
                true
        );
    }

    public DefaultFrame(BowlFrameConstraints constraints,
                        RollHandler rollsHandler,
                        RollHandler extraRollsHandler) {
        this.constraints = constraints;
        this.rollsHandler = rollsHandler;
        this.extraRollsHandler = extraRollsHandler;
    }

    @Override
    public Optional<Integer> addRoll(Integer knockedDownPins) {
        return rollsHandler.add(knockedDownPins);
    }

    @Override
    public Optional<Integer> addExtraRoll(Integer knockedDownPins) {
        Optional<Integer> result;
        if ((isStrike() && !strikeExtraRollsFinished()) ||
                (isSpare() && !spareExtraRollsFinished())) {
            result = extraRollsHandler.add(knockedDownPins);
        } else {
            log.warning("Extra roll rejected, no more extra rolls allowed.");
            result = Optional.empty();
        }
        return result;
    }

    @Override
    public Integer getScore() {
        return rollsHandler.getTotalKnockedDownPins() +
                extraRollsHandler.getTotalKnockedDownPins();
    }

    @Override
    public boolean isFinished() {
        return (isStrike() && strikeExtraRollsFinished()) ||
                (isSpare() && spareExtraRollsFinished()) ||
                (!(isStrike() | isSpare()) && rollsFinished());
    }

    @Override
    public boolean isStrike() {
        return Objects.equals(
                constraints.getMaxPins(),
                rollsHandler.getKnockedDownPins(0)
                        .orElse(0)
        );
    }

    @Override
    public boolean isSpare() {
        return !isStrike() && Objects.equals(
                constraints.getMaxPins(),
                rollsHandler.getTotalKnockedDownPins()
        );
    }

    @Override
    public Stream<Integer> getRolls() {
        return rollsHandler.getRolls();
    }

    @Override
    public Stream<Integer> getExtraRolls() {
        return extraRollsHandler.getRolls();
    }

    private boolean strikeExtraRollsFinished() {
        return rollsFinished(extraRollsHandler, constraints.getMaxExtraRollsIfStrike());
    }

    private boolean spareExtraRollsFinished() {
        return rollsFinished(extraRollsHandler, constraints.getMaxExtraRollsIfSpare());
    }

    private boolean rollsFinished() {
        return rollsFinished(rollsHandler, constraints.getMaxRolls());
    }

    private boolean rollsFinished(RollHandler rollsHandler, Integer maxRolls) {
        return Objects.equals(rollsHandler.getTotalKnockedDownPins(), rollsHandler.pinCapacity()) ||
                Objects.equals(maxRolls, rollsHandler.size());
    }

}
