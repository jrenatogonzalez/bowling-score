package com.rgonzalez.bowling.model;

import lombok.Getter;

import static com.rgonzalez.bowling.common.Validations.checkIfIsPositive;

@Getter
public class Chance {
    private final int pinFalls;
    private final boolean foul;

    private Chance(int pinFalls) {
        this.pinFalls = pinFalls;
        this.foul = false;
    }

    private Chance(boolean foul) {
        this.foul = foul;
        this.pinFalls = 0;
    }

    public static Chance with(int pinFalls) {
        checkIfIsPositive(pinFalls, "pinFalls");
        return new Chance(pinFalls);
    }

    public static Chance withFoul() {
        return new Chance(true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chance chance = (Chance) o;

        if (getPinFalls() != chance.getPinFalls()) return false;
        return isFoul() == chance.isFoul();
    }

    @Override
    public int hashCode() {
        int result = getPinFalls();
        result = 31 * result + (isFoul() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return isFoul() ? "F" : String.valueOf(getPinFalls());
    }
}
