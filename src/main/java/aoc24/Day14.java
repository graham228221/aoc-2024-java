package main.java.aoc24;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.aoc24.utils.InputFile;

public class Day14 {
    
    static Day14 day14 = new Day14();
    static final Boolean TEST_MODE = false;
    static final Boolean PART_TWO = true;

    static final int SPACE_WIDTH = TEST_MODE ? 11 : 101;
    static final int SPACE_HEIGHT = TEST_MODE ? 7 : 103;

    static final int TICKS = 10000;

    static ArrayList<Robot> robots = new ArrayList<Robot>();

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        System.out.println("bathroom dimensions: " + SPACE_WIDTH + " x " + SPACE_HEIGHT);

        parseRobots();

        for(int i = 0; i < TICKS; i++) {
            robots.forEach(r -> r.move());

            if(i == 99) {
                System.out.println("Positions after " + (i+1) + "seconds:");
                printMap();

                int midX = (SPACE_WIDTH - 1) / 2;
                int midY = (SPACE_HEIGHT - 1) / 2;

                int robotsInQ1 = robots.stream().filter(r -> r.xPosition < midX && r.yPosition < midY).collect(Collectors.toList()).size();
                int robotsInQ2 = robots.stream().filter(r -> r.xPosition > midX && r.yPosition < midY).collect(Collectors.toList()).size();
                int robotsInQ3 = robots.stream().filter(r -> r.xPosition < midX && r.yPosition > midY).collect(Collectors.toList()).size();
                int robotsInQ4 = robots.stream().filter(r -> r.xPosition > midX && r.yPosition > midY).collect(Collectors.toList()).size();

                System.out.println("Robots found in each quadrant: " + robotsInQ1 + ", " + robotsInQ2 + ", " + robotsInQ3 + ", " + robotsInQ4);
                System.out.println("Part 1 Result: " + (robotsInQ1 * robotsInQ2 * robotsInQ3 * robotsInQ4));
            }

            if(xmasTreeCheck()) {
                System.out.println("Found potential tree for Part 2 after " + (i+1) + "seconds:");
                printMap();
                break;
            }
        }
        
        System.out.println("time taken in milliseconds: " + (System.nanoTime() - startTime) / 1000000 );
    }

    static void parseRobots() {
        List<String> lines = TEST_MODE ? new InputFile("day14").testLines : new InputFile("day14").inputLines;

        lines.forEach(line -> {
            String position = line.split(" ")[0].replace("p=", "");
            String velocity = line.split(" ")[1].replace("v=", "");

            robots.add(day14.new Robot(position, velocity));
        });
    }

    static void printMap() {
        for(int i = 0; i < SPACE_HEIGHT; i++) {
            for(int j = 0; j < SPACE_WIDTH; j++) {
                int x = j;
                int y = i;

                int robotsInPosition = robots.stream().filter(r -> r.xPosition == x && r.yPosition == y).collect(Collectors.toList()).size();

                if(robotsInPosition > 0) {
                    System.out.print(robotsInPosition);
                } else {
                    System.out.print(".");
                }
                
            }
            System.out.println("");
        }
    }

    static Boolean xmasTreeCheck() {
        
        for(int i = 0; i < SPACE_HEIGHT; i++) {
            for(int j = 0; j < SPACE_WIDTH; j++) {
                int x = j;
                int y = i;

                int robotsInPosition = robots.stream().filter(r -> r.xPosition == x && r.yPosition == y).collect(Collectors.toList()).size();

                if(robotsInPosition > 1) {
                    return false;
                }
            }
        }

        return true;

    }

    class Robot {
        int xPosition;
        int yPosition;

        int xVelocity;
        int yVelocity;

        Robot(String position, String velocity) {
            String[] positions = position.split(",");
            xPosition = Integer.parseInt(positions[0]);
            yPosition = Integer.parseInt(positions[1]);

            String[] velocities = velocity.split(",");
            xVelocity = Integer.parseInt(velocities[0]);
            yVelocity = Integer.parseInt(velocities[1]);
        }

        void move() {
            if(xPosition + xVelocity >= SPACE_WIDTH) {
                xPosition = xPosition + xVelocity - SPACE_WIDTH;
            } else if(xPosition + xVelocity < 0) {
                xPosition = SPACE_WIDTH + xPosition + xVelocity;
            } else {
                xPosition += xVelocity;
            }

            if(yPosition + yVelocity >= SPACE_HEIGHT) {
                yPosition = yPosition + yVelocity - SPACE_HEIGHT;
            } else if(yPosition + yVelocity < 0) {
                yPosition = SPACE_HEIGHT + yPosition + yVelocity;
            } else {
                yPosition += yVelocity;
            }
        }
    }

}
