package com.rgonzalez.bowling;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.rgonzalez.bowling.IntegrationTestOutputs.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BowlingScoreAppIntegrationTest {
    private PrintStream originalPrintStream;
    private PrintStream printStream;
    private ByteArrayOutputStream baos;

    @BeforeEach
    public void setup() {
        originalPrintStream = System.out;
        baos = new ByteArrayOutputStream();
        printStream = new PrintStream(baos);
        System.setOut(printStream);
    }

    @AfterEach
    public void afterEach() {
        System.setOut(originalPrintStream);
        printStream.close();
    }

    @Test
    void main_WhenFileWithExampleRollouts_ShouldScore167And151() {
        String[] args = {"src/test/resources/bowling-game-normal.txt"};
        String result = getScore(args);
        assertThat(result).isEqualTo(NORMAL_GAME_OUTPUT);
    }

    @Test
    void main_WhenFileWithExampleRollouts2_ShouldScore143And142() {
        String[] args = {"src/test/resources/bowling-game-normal2.txt"};
        String result = getScore(args);
        assertThat(result).isEqualTo(NORMAL_GAME_OUTPUT2);
    }

    @Test
    void main_WhenFileWithPerfectGame_ShouldScore300() {
        String[] args = {"src/test/resources/bowling-game-perfect.txt"};
        String result = getScore(args);
        assertThat(result).isEqualTo(PERFECT_GAME_OUTPUT);
    }

    @Test
    void main_WhenFileWithAllZeros_ShouldScoreZero() {
        String[] args = {"src/test/resources/bowling-game-zero.txt"};
        String result = getScore(args);
        assertThat(result).isEqualTo(ZERO_GAME_OUTPUT);
    }

    @Test
    void main_WhenFileWithAllFouls_ShouldScoreZero() {
        String[] args = {"src/test/resources/bowling-game-foul.txt"};
        String result = getScore(args);
        assertThat(result).isEqualTo(FOUL_GAME_OUTPUT);
    }


    @Test
    void main_WhenFileWithInvalidData_ShouldPrintMessage() {
        String[] args = {"src/test/resources/bowling-game-invalid.txt"};
        String result = getScore(args);
        assertThat(result).contains(FILE_EMPTY_OR_INVALID);
    }

    @Test
    void main_WhenFileNotFound_ShouldPrintMessage() {
        String[] args = {"src/test/resources/file-not-exists.txt"};
        String result = getScore(args);
        assertThat(result).contains(FILE_NOT_FOUND);
    }

    @Test
    void main_NoFileSpecified_ShouldPrintMessage() {
        String[] args = null;
        String result = getScore(args);
        assertThat(result).contains(FILE_MUST_BE_SPECIFIED);
    }

    private String getScore(String[] filename) {
        BowlingScoreApp.main(filename);
        String result = baos.toString();
        return result;
    }

}
