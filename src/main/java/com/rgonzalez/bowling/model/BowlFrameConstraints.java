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
    private final Integer maxPins;
    private final Integer maxRolls;
    private final Integer maxExtraRollsIfSpare;
    private final Integer maxExtraRollsIfStrike;

    public BowlFrameConstraints() {
        this.maxPins = MAX_PINS;
        this.maxRolls = MAX_ROLLS;
        this.maxExtraRollsIfSpare = MAX_EXTRA_ROLLS_IF_SPARE;
        this.maxExtraRollsIfStrike = MAX_EXTRA_ROLLS_IF_STRIKE;
    }

    public BowlFrameConstraints(Integer maxPins, Integer maxRolls,
                                Integer maxExtraRollsIfSpare, Integer maxExtraRollsIfStrike) {
        this.maxPins = checkIfNonNullAndGreaterThan(maxPins, 0, "maxPins");
        this.maxRolls = checkIfNonNullAndGreaterThan(maxRolls, 0, "maxRolls");
        this.maxExtraRollsIfSpare = checkIfIsPositive(maxExtraRollsIfSpare, "maxExtraRollsIfSpare");
        this.maxExtraRollsIfStrike = checkIfIsPositive(maxExtraRollsIfStrike, "maxExtraRollsIfStrike");
    }
}
