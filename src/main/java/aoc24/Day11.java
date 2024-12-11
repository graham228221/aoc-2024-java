package main.java.aoc24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import main.java.aoc24.utils.InputFile;

public class Day11 {

    static final int ITERATIONS = 75;

    public static void main(String[] args) throws Exception {
    
        long startTime = System.nanoTime();
        //String input = new InputFile("day11").testLines.get(0);
        String input = new InputFile("day11").inputLines.get(0);

        //input = "125"; //try a single input

        //System.out.println("Part one attempt: " + partOne(input) ); //won't work for more than about 35 iterations!
        System.out.println("Part two attempt: " + partTwo(input) );

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;  
        System.out.println("time taken in milliseconds: " + duration );

    }

    static int partOne(String input) {
        List<String> stones = Arrays.asList(input.split(" "));

        for(int i = 0; i < ITERATIONS; i++) {
            stones = blink(stones);
        }

        return stones.size();
    }

    static List<String> blink(List<String> stones) {
        ArrayList<String> newStones = new ArrayList<String>();

        for(String stone : stones) {
            if(stone.equals("0")) {
                newStones.add("1");
            } else if((stone.length() & 1) == 0 ) { //is even
                String stoneOne = stone.substring(0, stone.length()/2);
                newStones.add(String.valueOf(Long.parseLong(stoneOne)));
                String stoneTwo = stone.substring(stone.length()/2);
                newStones.add(String.valueOf(Long.parseLong(stoneTwo)));
            } else {
                Long newStone = Long.parseLong(stone) * 2024;
                newStones.add(String.valueOf(newStone));
            }
        }

        return newStones;
    }

    static Long partTwo(String input) {
        
        List<String> stones = Arrays.asList(input.split(" "));

        Long result = 0L;

        for(String stone : stones) {
            Long stoneResult = keepBlinking(stone, 1L, 1);
            result += stoneResult;
        }

        return result;
    }

    static HashMap<String, Long> stoneDepthByTotal = new HashMap<String, Long>();


    static Long keepBlinking(String stone, Long stoneCount, int blinkNumber){
        
        if(blinkNumber > ITERATIONS){
            return 1L;
        }
        
        if(stoneDepthByTotal.containsKey(stone+"|"+blinkNumber)) {
            return stoneDepthByTotal.get(stone+"|"+blinkNumber);
        } 

        if(stone.equals("0")) {
            stoneCount = keepBlinking("1", stoneCount, blinkNumber+1);
        } else if((stone.length() & 1) == 0 ) { //is even
            stoneDepthByTotal.put(stone+"|"+blinkNumber, stoneCount+1);
            String stoneOne = stone.substring(0, stone.length()/2);
            String stoneTwo = stone.substring(stone.length()/2);
            stoneCount = keepBlinking(String.valueOf(Long.parseLong(stoneOne)), stoneCount, blinkNumber+1) + keepBlinking(String.valueOf(Long.parseLong(stoneTwo)), stoneCount, blinkNumber+1);
        } else {
            Long newStone = Long.parseLong(stone) * 2024;
            stoneCount = keepBlinking(String.valueOf(newStone), stoneCount, blinkNumber+1);
        }

        stoneDepthByTotal.put(stone+"|"+(blinkNumber), stoneCount);

        return stoneCount;

    }

}
