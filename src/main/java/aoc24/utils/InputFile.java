package main.java.aoc24.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class InputFile {
    
    public List<String> inputLines;

    public InputFile(String fileName) {
        inputLines = getLines(fileName);
    }

    private static List<String> getLines(String fileName) {
        try {

            BufferedReader br = new BufferedReader(
                new FileReader("src/main/java/aoc24/resources/"+fileName+".txt")
            );

            return br.lines().toList();

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

}
