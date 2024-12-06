package main.java.aoc24;

import java.util.Arrays;
import java.util.List;

import main.java.aoc24.utils.InputFile;

public class Day6 {

    static Day6 day6 = new Day6();

    static String[][] map;
    static int gridSize;
    static Guard guard;

    public static void main(String[] args) throws Exception {
        
        //List<String> lines = new InputFile("day6").testLines;
        List<String> lines = new InputFile("day6").inputLines;

        assert lines.size() == lines.get(0).length(); //is this a square grid?

        gridSize = lines.size();

        map = new String[gridSize][gridSize];

        for(int i = 0; i < gridSize; i++) {
            char[] row = lines.get(i).toCharArray();
            for(int j = 0; j < gridSize; j++) {
                String str = Character.toString(row[j]);
                map[i][j] = str;
                if(str.equals("^")) {
                    guard = day6.new Guard();
                    guard.yPos = i;
                    guard.xPos = j;
                    //System.out.println("The Guard is currently at " + guard.xPos + ", " + guard.yPos + " facing: " + guard.direction);
                }
            }
        }
        //System.out.println("Start map:");
        //printMap();

        while ( guard.xPos < gridSize -1 & guard.xPos > 0 && guard.yPos < gridSize -1 && guard.yPos > 0 ) {
            moveGuard();
            // System.out.println("The Guard is currently at " + guard.xPos + ", " + guard.yPos + " facing: " + guard.direction);
            // System.out.println("Current map:");
            // printMap();
        }

        map[guard.yPos][guard.xPos] = "X";
        System.out.println("Final map:");
        printMap();

        int result = 0;

        for(String[] row : map) { 
            for(String place : row) {
                if(place.equals("X")) {
                    result ++;
                }
            }
        }

        System.out.println("Part one result: " + result);
    }

    static void moveGuard() {
        if(guard.direction == "north") {
            if( map[guard.yPos - 1][guard.xPos].equals("#") ) { //turn east
                System.out.println("Turning east");
                guard.direction = "east";
                map[guard.yPos][guard.xPos] = ">";
            } else { //safe to move north
                map[guard.yPos][guard.xPos] = "X";
                guard.yPos = guard.yPos - 1;
                map[guard.yPos][guard.xPos] = "^";
            }
        } else if(guard.direction == "east") {
            if( map[guard.yPos][guard.xPos + 1].equals("#") ) { //turn south
                System.out.println("Turning south");
                guard.direction = "south";
                map[guard.yPos][guard.xPos] = "v";
            } else { //safe to move east
                map[guard.yPos][guard.xPos] = "X";
                guard.xPos = guard.xPos + 1;
                map[guard.yPos][guard.xPos] = ">";
            }

        } else if(guard.direction == "south") {
            if( map[guard.yPos + 1][guard.xPos].equals("#") ) { //turn west
                System.out.println("Turning west");
                guard.direction = "west";
                map[guard.yPos][guard.xPos] = "<";
            } else { //safe to move east
                map[guard.yPos][guard.xPos] = "X";
                guard.yPos = guard.yPos + 1;
                map[guard.yPos][guard.xPos] = "v";
            }

        }  else if(guard.direction == "west") {
            if( map[guard.yPos][guard.xPos - 1].equals("#") ) { //turn north
                System.out.println("Turning north");
                guard.direction = "north";
                map[guard.yPos][guard.xPos] = "^";
            } else { //safe to move east
                map[guard.yPos][guard.xPos] = "X";
                guard.xPos = guard.xPos - 1;
                map[guard.yPos][guard.xPos] = "<";
            }

        }
    }

    static void printMap() {
        for(String[] row : map) {
            System.out.println(String.join("", Arrays.asList(row)));
        }
    }

    class Guard {
        int xPos;
        int yPos;

        String direction = "north"; //default facing north

    }


}
