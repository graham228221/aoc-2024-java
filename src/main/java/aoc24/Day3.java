package main.java.aoc24;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import main.java.aoc24.utils.InputFile;

public class Day3 {

    static Day3 day3 = new Day3();
    static List<String> lines;

    static Boolean enabled = true;

    public static void main(String[] args) throws Exception {

        //lines = new InputFile("day3").testLines;                  //test
        lines = new InputFile("day3").inputLines;          //production

        Long partOneResult = 0L;
        Long partTwoResult = 0L;

        ArrayList<String> instructions = new ArrayList<String>();
        
        for(String line : lines) {
            instructions.addAll(findInstructions(line));
        }

        for(String instruction : instructions) {
            if(instruction.equals("do")) {
                enabled = true;
            } else if(instruction.equals("don't")) {
                enabled = false;
            } else {
                Mul mul = day3.new Mul(instruction);
                partOneResult += mul.product;
                if(enabled) {
                    partTwoResult += mul.product;
                }
            }
        }

        System.out.println("part 1 result: "+ partOneResult);
        System.out.println("part 2 result: "+ partTwoResult);
    }

    private static ArrayList<String> findInstructions(String input) {
        
        ArrayList<String> instructions = new ArrayList<>();
        
        Pattern stringPattern = Pattern.compile("mul\\([0-9]+,[0-9]+\\)|don't|do");
        Matcher matcher = stringPattern.matcher(input);
        Stream<String> matchResults = matcher.results().map(MatchResult :: group);

        matchResults.forEach(instructions :: add);

        return instructions;
    }

    private class Mul {
        long product; 

        private Mul(String mulString) {
            String strippedMul = mulString.replace("mul(", "").replace(")","");
            var splitMul = strippedMul.split(",");
            product = Integer.parseInt(splitMul[0]) * Integer.parseInt(splitMul[1]);
        }
    }



}
