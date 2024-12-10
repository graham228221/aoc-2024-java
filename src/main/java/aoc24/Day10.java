package main.java.aoc24;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import main.java.aoc24.utils.InputFile;

public class Day10 {

    static Day10 day10 = new Day10();

    static Integer[][] map;
    static int gridSize;
    static ArrayList<String> trailHeadCoords = new ArrayList<String>();

    static HashSet<String> currentTrailHeadPeaks = new HashSet<String>();
    static int currentRating;

    public static void main(String[] args) throws Exception {
        buildMap();

        int partOneResult = 0;
        int partTwoResult = 0;

        for(String trailHeadCoord : trailHeadCoords) {
            
            //reset count properties for new trail head
            currentTrailHeadPeaks = new HashSet<String>(); //unique peaks for this trail head (part one)
            currentRating = 0; //all trails leading to a peak from this trail head (part two)
            
            String[] coordinates = trailHeadCoord.split(",");

            tryValidNextSteps(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), 0);

            partOneResult += currentTrailHeadPeaks.size();
            partTwoResult += currentRating;

        }

        System.out.println("Part one result: " + partOneResult);
        System.out.println("Part two result: " + partTwoResult);
    }

    static void buildMap(){
        
        System.out.println("*** Building map... ***");
        
        //List<String> lines = new InputFile("day10").testLines;
        List<String> lines = new InputFile("day10").inputLines;

        assert lines.size() == lines.get(0).length();
        gridSize = lines.size();

        map = new Integer[gridSize][gridSize];
        for(int y = 0; y < gridSize; y++) {
            char[] row = lines.get(y).toCharArray();
            for(int x = 0; x < gridSize; x++) {
                map[y][x] = Character.getNumericValue(row[x]);
                if(Character.getNumericValue(row[x]) == 0) {
                    trailHeadCoords.add(x + "," + y);
                }
            }
        }
    }

    static void tryValidNextSteps(int x, int y, int value) {
        if(value == 9) {
            currentTrailHeadPeaks.add(x + "," + y);
            currentRating ++;
        } else {
            if(x > 0) {
                if(map[y][x-1] == value + 1) {
                    tryValidNextSteps(x-1, y, value+1);
                }
            }
            if(x < gridSize - 1) {
                if(map[y][x+1] == value + 1) {
                    tryValidNextSteps(x+1, y, value+1);
                }
            }
            if(y > 0) {
                if(map[y-1][x] == value + 1) {
                    tryValidNextSteps(x, y-1, value+1);
                }
            }
            if(y < gridSize - 1) {
                if(map[y+1][x] == value + 1) {
                    tryValidNextSteps(x, y+1, value+1);
                }
            }
        }
    }

}
