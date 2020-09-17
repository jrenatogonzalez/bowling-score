package com.rgonzalez.bowling.model;

import lombok.Getter;

@Getter
public class BowlerResults {
    private String bowlerName;
    private Integer result;

    public BowlerResults(String bowlerName, Integer result) {
        this.bowlerName = bowlerName;
        this.result = result;
    }

    @Override
    public String toString() {
        return "BowlerResults{" +
                "bowlerName='" + bowlerName + '\'' +
                ", result=" + result +
                '}';
    }
}
