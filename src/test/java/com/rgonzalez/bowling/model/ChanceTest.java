package com.rgonzalez.bowling.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ChanceTest {

    @Test
    void with() {
        Chance chance = Chance.with(7);
        assertThat(chance.getPinFalls()).isEqualTo(7);
        assertThat(chance.isFoul()).isFalse();
    }

    @Test
    void withFoul() {
        Chance chance = Chance.withFoul();
        assertThat(chance.getPinFalls()).isEqualTo(0);
        assertThat(chance.isFoul()).isTrue();
    }

    @Test
    void toString_WhenNotFoul_ShouldReturnPinFalls() {
        Chance chance = Chance.with(7);
        assertThat(chance.toString()).isEqualTo("7");
    }

    @Test
    void toString_WhenFoul_ShouldReturnF() {
        Chance chance = Chance.withFoul();
        assertThat(chance.toString()).isEqualTo("F");
    }
}
