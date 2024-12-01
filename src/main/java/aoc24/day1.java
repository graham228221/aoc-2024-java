package main.java.aoc24;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import main.java.aoc24.utils.InputFile;

public class Day1 {

        private static ArrayList<Integer> leftList;
        private static ArrayList<Integer> rightList;

        public static void main(String[] args) throws Exception {

        Day1 day1 = new Day1();
        
        //var lines = new InputFile("day1").testLines;
        var lines = new InputFile("day1").inputLines;

        Input parsedInput = day1.new Input(lines);

        leftList = parsedInput.leftList;
        rightList = parsedInput.rightList;

        int partOneResult = partOne();
        Long partTwoResult = partTwo();

        System.out.println("part 1 result: "+ partOneResult);
        System.out.println("part 2 result: "+ partTwoResult);
    }

    private static int partOne() {
        
        int result = 0;
        
        for(int i = 0; i < leftList.size(); i++) {
            result +=Math.abs(leftList.get(i) - rightList.get(i));
        }

        return result;
    }

    private static Long partTwo() {
        Long result = 0L;

        Map<Integer, Long> countOfEachNumber = rightList.stream().collect(
            Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
            )
        );
        
        for(int i = 0; i < leftList.size(); i++) {

            Long instances = countOfEachNumber.get(leftList.get(i));
            Long lineResult = instances != null ? leftList.get(i) * instances : 0;
            result += lineResult;
        }
        return result;
    }

    public class Input {
        ArrayList<Integer> leftList = new ArrayList<Integer>();
        ArrayList<Integer> rightList = new ArrayList<Integer>();

        public Input(List<String> lines) {
            for(String line : lines) {
                var splitLine = line.split("   ");
    
                leftList.add(Integer.parseInt(splitLine[0]));
                rightList.add(Integer.parseInt(splitLine[1]));
            }

            leftList.sort(null);
            rightList.sort(null);
        }
    }

}
