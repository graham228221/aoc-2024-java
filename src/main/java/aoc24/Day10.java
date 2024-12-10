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

     //haha
    static HashSet<String> currentTrailHeadPeaks = new HashSet<String>();
    static int currentRating;

    static Boolean partTwo = true;

    public static void main(String[] args) throws Exception {
        buildMap();

        int result = 0;

        for(String trailHeadCoord : trailHeadCoords) {
            String[] coordinates = trailHeadCoord.split(",");

            result += getTrailHeadScore(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
        }

        System.out.println("Result: " + result);
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

    static int getTrailHeadScore(int x, int y) {
        //reset for this trail head
        currentTrailHeadPeaks = new HashSet<String>(); //unique peaks
        currentRating = 0; //all paths leading to a peak

        day10.new Coordinate(x, y, 0);

        return partTwo ? currentRating : currentTrailHeadPeaks.size();
    }

    static void tryValidNextSteps(int x, int y, int value) {
        if(x > 0) {
            if(map[y][x-1] == value + 1) {
                    day10.new Coordinate(x-1, y, value+1);
            }
        }
        if(x < gridSize - 1) {
            if(map[y][x+1] == value + 1) {
                    day10.new Coordinate(x+1, y, value+1);
            }
        }
        if(y > 0) {
            if(map[y-1][x] == value + 1) {
                    day10.new Coordinate(x, y-1, value+1);
            }
        }
        if(y < gridSize - 1) {
            if(map[y+1][x] == value + 1) {
                    day10.new Coordinate(x, y+1, value+1);
            }
        }
    }


    class Coordinate {
        int x;
        int y;
        int value;

        Coordinate(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;

            if(value < 9) {
                tryValidNextSteps(x, y, value);
            } else {
                currentTrailHeadPeaks.add(x + "," + y);
                currentRating ++;
            }
        }
    }

}
