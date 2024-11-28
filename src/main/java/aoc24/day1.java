package main.java.aoc24;

import main.java.aoc24.utils.InputFile;

public class day1 {

        public static void main(String[] args) throws Exception {
        var lines = new InputFile("day1").inputLines;
        int result = 0;
        for(String line : lines) {
            System.out.println(line);
        }

        System.out.println("result: "+ result);
    }

}
