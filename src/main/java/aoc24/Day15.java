package main.java.aoc24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import main.java.aoc24.utils.InputFile;

public class Day15 {

    static Day15 day15 = new Day15();
    static final Boolean TEST_MODE = false;

    static String[][] map; // [y][x]
    //static int mapSize;

    static int width;
    static int height;
    static Robot robot = day15.new Robot();

    // static final String WALL = "#";

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        
        buildMap();
        System.out.println("Initial state:");
        printMap();

        for(String instr : getInstructions()) {
            //System.out.println("*** NEW INSTRUCTION: " + instr + " ***");
            moveRobot(instr);

            //printMap();
            //Thread.sleep(50);
        }

        Long result = 0L;

        for(int y = 0; y < height; y++) {
            for(int x =0; x < width; x++) {
                if(map[y][x].equals("O")) {
                    result += 100 * y + x;
                }
            }
        }

        System.out.println("Part one result: " + result);

        System.out.println("time taken in milliseconds: " + (System.nanoTime() - startTime) / 1000000 );

    }

    static void moveRobot(String direction) {

        //System.out.println("Robot in space: " + robot.x + "," + robot.y);
        Boolean canMove = false;

        if(direction.equals("<")) { 
            int max = robot.x-1;
            int min = max;

            findWallOrSpace:
            for(int i = max; i >= 0; i--) {
                String spot = map[robot.y][i];
                if(spot.equals("#")) {
                    break findWallOrSpace;
                } else if(spot.equals(".")) {
                    min = i;
                    canMove = true;
                    break findWallOrSpace;
                }
                min = i;
            }

            if(canMove){
                //System.out.println("Space to move left");
                map[robot.y][robot.x] = ".";
                for(int i = min; i <= max; i++) {
                    if(i == max) {
                        //System.out.println("Put robot in " + i + "," + robot.y);
                        map[robot.y][i] = "@";
                        robot.x = i;
                    } else {
                        //System.out.println("Put box in " + i + "," + robot.y);
                        map[robot.y][i] = "O";
                    }
                }
            } else {
                //System.out.println("No space to move left");
            }

        } else if(direction.equals("^")) { 
            int max = robot.y-1;
            int min = max;
            
            findWallOrSpace:
            for(int i = max; i >= 0; i--) {
                String spot = map[i][robot.x];
                if(spot.equals("#")) {
                    break findWallOrSpace;
                } else if(spot.equals(".")) {
                    min = i;
                    canMove = true;
                    break findWallOrSpace;
                }
                min = i;
            }

            if(canMove){
                //System.out.println("Space to move up");
                map[robot.y][robot.x] = ".";
                for(int i = min; i <= max; i++) {
                    if(i == max) {
                        //System.out.println("Put robot in " + robot.x + "," + i);
                        map[i][robot.x] = "@";
                        robot.y = i;
                    } else {
                        //System.out.println("Put box in " + robot.x + "," + i);
                        map[i][robot.x] = "O";
                    }
                }
            } else {
                //System.out.println("No space to move up");
            }
        } else if(direction.equals(">")) { 
            int min = robot.x+1;
            int max = min;
            
            findWallOrSpace:
            for(int i = min; i < width; i++) {
                String spot = map[robot.y][i];
                if(spot.equals("#")) {
                    //System.out.println("Found wall at " + i + "," + robot.y);
                    break findWallOrSpace;
                } else if(spot.equals(".")) {
                    //System.out.println("Found space at " + i + "," + robot.y);
                    max = i;
                    canMove = true;
                    break findWallOrSpace;
                }
                max = i;
            }

            //System.out.println("Can move places: " + min + " to " + max);

            if(canMove){
                //System.out.println("Space to move right");
                map[robot.y][robot.x] = ".";
                for(int i = min; i <= max; i++) {
                    if(i == min) {
                        //System.out.println("Put robot in " + i + "," + robot.y);
                        map[robot.y][i] = "@";
                        robot.x = i;
                    } else {
                        //System.out.println("Put box in " + i + "," + robot.y);
                        map[robot.y][i] = "O";
                    }
                }
            } else {
                //System.out.println("No space to move right");
            }

        } else if(direction.equals("v")) { 
            int min = robot.y+1;
            int max = min;
            
            findWallOrSpace:
            for(int i = min; i < height; i++) {
                String spot = map[i][robot.x];
                if(spot.equals("#")) {
                    break findWallOrSpace;
                } else if(spot.equals(".")) {
                    max = i;
                    canMove = true;
                    break findWallOrSpace;
                }
                max = i;
            }

            if(canMove){
                map[robot.y][robot.x] = ".";
                for(int i = min; i <= max; i++) {
                    if(i == min) {
                        map[i][robot.x] = "@";
                        robot.y = i;
                    } else {
                        map[i][robot.x] = "O";
                    }
                }
            } else {
                //System.out.println("No space to move down");
            }
        }

    }

    static String[] getInstructions() {
        List<String> lines = TEST_MODE ? new InputFile("day15").testLines : new InputFile("day15").inputLines;

        return lines.stream().collect(Collectors.joining()).split("");
    }

    static void buildMap() {
        
        String fileName = TEST_MODE ? "src/main/java/aoc24/resources/day15_exampleMap.txt" : "src/main/java/aoc24/resources/day15_map.txt";
        
        try (BufferedReader br = new BufferedReader(
            new FileReader(fileName)
        )) {
            List<String> lines = br.lines().toList();

            height = lines.size();
            width = lines.get(0).length();
            map = new String[height][width];
            for(int y = 0; y < height; y++) {
                char[] row = lines.get(y).toCharArray();
                for(int x = 0; x < width; x++) {
                    map[y][x] = String.valueOf(row[x]);
                    if(map[y][x].equals("@")) {
                        robot.x = x;
                        robot.y = y;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    static void printMap() {
        
        String strMap = "";
        for(String[] row : map) {
            strMap += String.join("", Arrays.asList(row)) + "\n";
        }

        System.out.println(strMap);
    }

    class Robot {
        int x;
        int y;
    }

}
