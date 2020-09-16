package com.rgonzalez.bowling.model;

import lombok.Getter;

import static com.rgonzalez.bowling.common.Validations.checkIfIsPositive;
import static com.rgonzalez.bowling.common.Validations.checkIfNonNullAndGreaterThan;

@Getter
public class BowlFrameConstraints implements FrameConstraints {
    public static final Integer MAX_PINS = 10;
    public static final Integer MAX_ROLLS = 2;
    public static final Integer MAX_EXTRA_ROLLS_IF_SPARE = 1;
    public static final Integer MAX_EXTRA_ROLLS_IF_STRIKE = 2;
    private static final Integer MAX_FRAMES = 10;
    private final Integer maxPins;
    private final Integer maxRolls;
    private final Integer maxExtraRollsIfSpare;
    private final Integer maxExtraRollsIfStrike;
    private final Integer maxFrames;

    public BowlFrameConstraints() {
        this.maxPins = MAX_PINS;
        this.maxRolls = MAX_ROLLS;
        this.maxExtraRollsIfSpare = MAX_EXTRA_ROLLS_IF_SPARE;
        this.maxExtraRollsIfStrike = MAX_EXTRA_ROLLS_IF_STRIKE;
        this.maxFrames = MAX_FRAMES;
    }

    public BowlFrameConstraints(
            Integer maxPins,
            Integer maxRolls,
            Integer maxExtraRollsIfSpare,
            Integer maxExtraRollsIfStrike,
            Integer maxFrames) {
        this.maxPins = checkIfNonNullAndGreaterThan(maxPins, 0, "maxPins");
        this.maxRolls = checkIfNonNullAndGreaterThan(maxRolls, 0, "maxRolls");
        this.maxExtraRollsIfSpare = checkIfIsPositive(maxExtraRollsIfSpare, "maxExtraRollsIfSpare");
        this.maxExtraRollsIfStrike = checkIfIsPositive(maxExtraRollsIfStrike, "maxExtraRollsIfStrike");
        this.maxFrames = checkIfIsPositive(maxFrames, "maxFrames");
    }
}
