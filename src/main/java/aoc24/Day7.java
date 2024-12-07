package main.java.aoc24;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import main.java.aoc24.utils.InputFile;

public class day7 {

    static Boolean partTwoRunning = true;

    public static void main(String[] args) throws Exception {
        //List<String> lines = new InputFile("day7").testLines;
        List<String> lines = new InputFile("day7").inputLines;

        Long endResult = 0L;

        for(String line : lines) {

            String[] splitLine = line.split(":");
            Long target = Long.parseLong(splitLine[0]);
            HashSet<String> potentialSolutions = getPossibleSolutions(splitLine[1]);

            HashSet<String> correctSolutions = new HashSet<String>();

            for(String equation : potentialSolutions ) {
                
                Long result = getResult(equation);

                if(result.equals(target)) {
                    correctSolutions.add(equation);
                }
            }

            if(correctSolutions.size() > 0) {
                endResult += target;
            }
            
        }

        System.out.println("result: " + endResult);

    }

    static HashSet<String> getPossibleSolutions(String input) {

        List<Integer> numbers = Arrays.stream(input.trim().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        HashSet<String> potentialSolutions = new HashSet<String>();

        for(Integer number : numbers) {
            if(potentialSolutions.isEmpty()) {
                potentialSolutions.add(number.toString());
                potentialSolutions.add(number.toString());
                if(partTwoRunning) {
                    potentialSolutions.add(number.toString());
                }
                
            } else {
                HashSet<String> newPotentialSolutions = new HashSet<String>();
                for(String partialSolution : potentialSolutions) {
                    newPotentialSolutions.add(partialSolution + " + " + number);
                    newPotentialSolutions.add(partialSolution + " * " + number);
                    if(partTwoRunning) {
                        newPotentialSolutions.add(partialSolution + " xx " + number);
                    }
                }
                potentialSolutions = newPotentialSolutions;
            }
        }

        return potentialSolutions;
    }

    static Long getResult(String strEquation) {

        String[] splitEquation = strEquation.split(" ");

        Long result = Long.parseLong(splitEquation[0]);

        for(int i = 0; i < splitEquation.length; i++) {
            if(splitEquation[i].equals("xx")) {
                result = Long.parseLong(result.toString() + splitEquation[i+1]);
            } else if(splitEquation[i].equals("+")) {
                result = result + Integer.parseInt(splitEquation[i+1]);
            } else if(splitEquation[i].equals("*")) {
                result = result * Integer.parseInt(splitEquation[i+1]);
            }
        }

        return result;
    }
    
}
