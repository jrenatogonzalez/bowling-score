package com.rgonzalez.bowling.model;

import lombok.Getter;

@Getter
public class BowlerResults {
    private String bowlerName;
    private Chance chance;

    public BowlerResults(String bowlerName, Chance chance) {
        this.bowlerName = bowlerName;
        this.chance = chance;
    }
}
