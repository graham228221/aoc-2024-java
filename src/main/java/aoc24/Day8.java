package main.java.aoc24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import main.java.aoc24.utils.InputFile;

public class Day8 {
    static Day8 day8 = new Day8();
    
    static String[][] map;
    static int gridSize;

    static ArrayList<Node> nodes = new ArrayList<Node>();
    static HashSet<String> antinodeSet = new HashSet<String>();
    static HashSet<String> antinodeSetPartTwo = new HashSet<String>();


    public static void main(String[] args) throws Exception {
        //List<String> lines = new InputFile("day8").testLines;
        List<String> lines = new InputFile("day8").inputLines;

        assert lines.size() == lines.get(0).length(); //is this a square grid?
        gridSize = lines.size();

        map = new String[gridSize][gridSize];

        for(int i = 0; i < gridSize; i++) {
            char[] row = lines.get(i).toCharArray();
            for(int j = 0; j < gridSize; j++) {
                String str = Character.toString(row[j]);
                map[i][j] = str; //map[y][x]
                if(!str.equals(".")) {
                    Node node = day8.new Node(i,j,str);
                    nodes.add(node);
                }
            }
        }

        for(int i = 0; i < nodes.size(); i++) {
            Node nodeOne = nodes.get(i);
            for(int j = i; j < nodes.size(); j++) {
                Node nodeTwo = nodes.get(j);
                if(i!=j && nodeOne.type.equals(nodeTwo.type)) {
                    //any pair of antennae will themselves by antinodes in part 2
                    antinodeSetPartTwo.add(nodeOne.x + "," + nodeOne.y);
                    antinodeSetPartTwo.add(nodeTwo.x + "," + nodeTwo.y);

                    int yA = nodeOne.y + (nodeOne.y - nodeTwo.y);
                    int xA = nodeOne.x + (nodeOne.x - nodeTwo.x);
                    
                    if(yA >= 0 && yA < gridSize && xA >= 0 && xA < gridSize){
                        map[yA][xA] = "#";
                        antinodeSet.add(xA + "," + yA);
                        antinodeSetPartTwo.add(xA + "," + yA);
                        
                    }

                    int yB = nodeTwo.y - (nodeOne.y - nodeTwo.y);
                    int xB = nodeTwo.x - (nodeOne.x - nodeTwo.x);
                    
                    if(yB >= 0 && yB < gridSize && xB >= 0 && xB < gridSize){
                        map[yB][xB] = "#";
                        antinodeSet.add(xB + "," + yB);
                        antinodeSetPartTwo.add(xB + "," + yB);
                    }

                    //part two: keep adding antinodes until we get to the edge of the map
                    yA = yA + (nodeOne.y - nodeTwo.y);
                    xA = xA + (nodeOne.x - nodeTwo.x);

                    while(yA >= 0 && yA < gridSize && xA >= 0 && xA < gridSize) {                        
                        map[yA][xA] = "#";
                        antinodeSetPartTwo.add(xA + "," + yA);

                        yA = yA + (nodeOne.y - nodeTwo.y);
                        xA = xA + (nodeOne.x - nodeTwo.x);
                    }

                    yB = yB - (nodeOne.y - nodeTwo.y);
                    xB = xB - (nodeOne.x - nodeTwo.x);

                    while(yB >= 0 && yB < gridSize && xB >= 0 && xB < gridSize) {
                        map[yB][xB] = "#";
                        antinodeSetPartTwo.add(xB + "," + yB);

                        yB = yB - (nodeOne.y - nodeTwo.y);
                        xB = xB - (nodeOne.x - nodeTwo.x);
                    }  
                }
            }
        }

        printMap();

        System.out.println("Part one result: " + antinodeSet.size());
        System.out.println("Part two result: " + antinodeSetPartTwo.size());
    }

    static void printMap() {
        for(String[] row : map) {
            System.out.println(String.join("", Arrays.asList(row)));
        }
    }

    class Node {
        int y;
        int x;
        String type; 

        Node(int yValue, int xValue, String value) {
            this.y = yValue;
            this.x = xValue;
            this.type = value;
        }
    }
}
