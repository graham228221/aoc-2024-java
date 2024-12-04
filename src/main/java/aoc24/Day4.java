package main.java.aoc24;

import java.util.List;

import main.java.aoc24.utils.InputFile;

public class Day4 {

    static String[][] wordsearch;
    static int gridSize;

    public static void main(String[] args) throws Exception {

        //List<String> lines = new InputFile("day4").testLines;                  //test
        List<String> lines = new InputFile("day4").inputLines;          //production

        assert lines.size() == lines.get(0).length();

        gridSize = lines.size();

        wordsearch = new String[gridSize][gridSize];

        for(int i = 0; i < gridSize; i++) {
            char[] row = lines.get(i).toCharArray();
            for(int j = 0; j < gridSize; j++) {
                wordsearch[i][j] = Character.toString(row[j]);
            }
        }

        System.out.println("part one result: " + getPartOne());
        System.out.println("part two result: " + getPartTwo());

    }

    static int getPartOne(){
        
        int result = 0;
        
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                if(wordsearch[i][j].equals("X")) {
                    if(gridSize - j > 3) {
                        //it might be horizontal
                        if(wordsearch[i][j+1].equals("M") && wordsearch[i][j+2].equals("A") && wordsearch[i][j+3].equals("S")){
                            result ++;
                        }
                    }
                    if(j > 2) {
                        //it might be horizontal backwards
                        if(wordsearch[i][j-1].equals("M") && wordsearch[i][j-2].equals("A") && wordsearch[i][j-3].equals("S")){
                            result ++;
                        }
                    }
                    if(gridSize - i > 3) {
                        //it might be vertical
                        if(wordsearch[i+1][j].equals("M") && wordsearch[i+2][j].equals("A") && wordsearch[i+3][j].equals("S")){
                            result ++;
                        }
                    }
                    if(i > 2) {
                        //it might be vertical backwards
                        if(wordsearch[i-1][j].equals("M") && wordsearch[i-2][j].equals("A") && wordsearch[i-3][j].equals("S")){
                            result ++;
                        }
                    }
                    if(gridSize - j > 3 && gridSize - i > 3) {
                        //it might be diagonal towards bottom right
                        if(wordsearch[i+1][j+1].equals("M") && wordsearch[i+2][j+2].equals("A") && wordsearch[i+3][j+3].equals("S")){
                            result ++;
                        }
                    }
                    if(j > 2 && i > 2) {
                        //it might be diagonal towards top left
                        if(wordsearch[i-1][j-1].equals("M") && wordsearch[i-2][j-2].equals("A") && wordsearch[i-3][j-3].equals("S")){
                            result ++;
                        }
                    }
                    if(j > 2 && gridSize - i > 3) {
                        //it might be diagonal towards bottom left
                        if(wordsearch[i+1][j-1].equals("M") && wordsearch[i+2][j-2].equals("A") && wordsearch[i+3][j-3].equals("S")){
                            result ++;
                        }
                    }
                    if(i > 2 && gridSize - j > 3) {
                        //it might be diagonal towards top right
                        if(wordsearch[i-1][j+1].equals("M") && wordsearch[i-2][j+2].equals("A") && wordsearch[i-3][j+3].equals("S")){
                            result ++;
                        }
                    }
                }
            }
        }

        return result;
    }

    static int getPartTwo(){
        
        int result = 0;
        
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                if(wordsearch[i][j].equals("A") && j > 0 && i > 0 && gridSize - j > 1 && gridSize - i > 1) {
                    //an A has enough padding!
                    if(
                        ((
                            //top-left to bottom-right
                            wordsearch[i-1][j-1].equals("M") &&
                            wordsearch[i+1][j+1].equals("S")
                        ) ||
                        (
                            //bottom-right to top-left
                            wordsearch[i+1][j+1].equals("M") &&
                            wordsearch[i-1][j-1].equals("S")
                        )) &&
                        ((
                            //top-right to bottom-left
                            wordsearch[i-1][j+1].equals("M") &&
                            wordsearch[i+1][j-1].equals("S")
                        ) ||
                        (
                            //bottom-left to top-right
                            wordsearch[i+1][j-1].equals("M") &&
                            wordsearch[i-1][j+1].equals("S")
                        ))
                    ) {
                        result ++;
                    }
                }
            }
        }

        return result;
    }
}
