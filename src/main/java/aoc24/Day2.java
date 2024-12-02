package main.java.aoc24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.aoc24.utils.InputFile;

public class Day2 {

    private static HashMap<Integer, List<Integer>> reports;
    private static Day2 day2 = new Day2();

    public static void main(String[] args) throws Exception {

        //var lines = new InputFile("day2").testLines;
        var lines = new InputFile("day2").inputLines;

        reports = getReports(lines);

        int partOneResult = partOne();
        int partTwoResult = partTwo();

        System.out.println("part 1 result: "+ partOneResult);
        System.out.println("part 2 result: "+ partTwoResult);
    }

    private static HashMap<Integer, List<Integer>> getReports(List<String> lines) {
        HashMap<Integer, List<Integer>> reports = new HashMap<Integer, List<Integer>>();

        for(int i = 0; i < lines.size(); i++){
            
            ArrayList<Integer> levels = new ArrayList<Integer>();

            for(String str : lines.get(i).split(" ")) {
                levels.add(Integer.parseInt(str));
            }
            
            reports.put(i, levels);
        }

        return reports;
    }

    private static int partOne() {
        int result = 0;

        for(int i = 0; i < reports.size(); i++) {
            Report report = day2.new Report(reports.get(i));
            if(report.isSafe) {
                result ++;
            }
        }

        return result;
    }

    private static int partTwo() {
        int result = 0;

        for(int i = 0; i < reports.size(); i++) {
            Report report = day2.new Report(reports.get(i));
            
            if(report.isSafe) {
                result ++;
            } else {
                
                checkWithReportsRemoved:
                for(int j = 0; j < reports.get(i).size(); j++) {
                    ArrayList<Integer> newReports = new ArrayList<Integer>(reports.get(i));
                    newReports.remove(j);
                    Report newReport = day2.new Report(newReports);

                    if(newReport.isSafe) {
                        result ++;
                        break checkWithReportsRemoved;
                    }
                }

            }
        }

        return result;
    }

    public class Report {

        public Boolean isSafe = true;

        public Report(List<Integer> reportValues) {
                Boolean isIncreasing = reportValues.get(1) - reportValues.get(0) > 0;
    
                for(int i = 1; i < reportValues.size(); i++) {
                    Integer prevLevel = reportValues.get(i-1);
                    Integer thisLevel = reportValues.get(i);
    
                    if(!levelIsSafe(prevLevel, thisLevel, isIncreasing)) {
                        isSafe = false;
                        break;
                    }
                }
    
            }
    }

    private static Boolean levelIsSafe(Integer prevLevel, Integer nextLevel, Boolean shouldIncrease) {
        if(shouldIncrease) {
            return nextLevel > prevLevel && nextLevel - prevLevel < 4;
        } else {
            return prevLevel > nextLevel && prevLevel - nextLevel < 4;
        }
    }
}
