package com.rgonzalez.bowling.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileUtils {

    public String getFileName(String[] args) {
        return Arrays.stream(args)
                .collect(Collectors.joining(" "));
    }

    public BufferedReader getReader(String[] filenameInArgs) throws IOException {
        String filename = getFileName(filenameInArgs);
        return Files.newBufferedReader(Paths.get(filename));
    }

}
