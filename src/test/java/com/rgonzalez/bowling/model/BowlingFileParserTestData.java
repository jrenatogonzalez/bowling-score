package com.rgonzalez.bowling.model;

import java.io.BufferedReader;
import java.io.StringReader;

public class BowlingFileParserTestData {
    public static BufferedReader getValidResultsTestData() {
        String validResults = "Jeff\t10\n" +
                "John\t3\n" +
                "John\t7\n" +
                "Jeff\t7\n" +
                "Jeff\t3\n" +
                "John\t6\n" +
                "John\t3\n" +
                "Jeff\t9\n" +
                "Jeff\t0\n" +
                "John\t10\n" +
                "Jeff\t10\n" +
                "John\t8\n" +
                "John\t1\n" +
                "Jeff\t0\n" +
                "Jeff\t8\n" +
                "John\t10\n" +
                "Jeff\t8\n" +
                "Jeff\t2\n" +
                "John\t10\n" +
                "Jeff\tF\n" +
                "Jeff\t6\n" +
                "John\t9\n" +
                "John\t0\n" +
                "Jeff\t10\n" +
                "John\t7\n" +
                "John\t3\n" +
                "Jeff\t10\n" +
                "John\t4\n" +
                "John\t4\n" +
                "Jeff\t10\n" +
                "Jeff\t8\n" +
                "Jeff\t1\n" +
                "John\t10\n" +
                "John\t9\n" +
                "John\t0\n";
        return new BufferedReader(new StringReader(validResults));
    }

    public static BufferedReader getSomeValidResultsTestData() {
        String validResults = "Jeff\t10\n" +
                "John\tS\n" + // Not valid
                "" + // Not valid
                "   " + // Not valid
                "Jeff\t3122423423111121218881\n" + //May be
                "John\t23423FRRRR\n" + // Not valid
                "\t3\n" +        //Not valid, empty name
                "Jeff 9\n" +     //Not valid, it's not separated by tab
                "\t\n" +     //Not valid
                "\t\t\n" +     //Not valid
                "\n" + // Not Valid
                "John\tFFFFF\n" + // Not valid
                "Jeff\t0\n" +
                "John\t10\n" +
                "Jeff\t10\n" +
                "John\t8\n" +
                "John\t1\n" +
                "Jeff\t0\n" +
                "Jeff\t8\n" +
                "John\t10\n" +
                "Jeff\t8\n" +
                "Jeff\t2\n" +
                "John\t10\n" +
                "Jeff\tF\n" +
                "Jeff\t6\n" +
                "John\t9\n" +
                "John\t0\n" +
                "Jeff\t10\n" +
                "John\t7\n" +
                "John\t3\n" +
                "Jeff\t10\n" +
                "John\t4\n" +
                "John\t4\n" +
                "Jeff\t10\n" +
                "Jeff\t8\n" +
                "Jeff\t1\n" +
                "John\t10\n" +
                "John\t9\n" +
                "John\t0\n";
        return new BufferedReader(new StringReader(validResults));
    }

}
