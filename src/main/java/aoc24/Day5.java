package main.java.aoc24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import main.java.aoc24.utils.InputFile;

public class Day5 {

    static Day5 day5 = new Day5();
    static HashMap<Integer, Page> pages;
    static ArrayList<List<Integer>> updates;

    public static void main(String[] args) throws Exception {
        buildPageRules();
        getResult();
    }

    static void buildPageRules() {
        pages = new HashMap<Integer, Page>();
        updates = new ArrayList<List<Integer>>();

        //List<String> lines = new InputFile("day5").testLines;
        List<String> lines = new InputFile("day5").inputLines;

        for(String line : lines) {
            if(line.contains("|")) {
                String[] splitLine = line.split("\\|");
                int before = Integer.parseInt(splitLine[0]), after = Integer.parseInt(splitLine[1]);
                if(pages.containsKey(before)) {
                    Page page = pages.get(before);
                    page.pagesAfter.add(after);
                } else {
                    Page newPage = day5.new Page();
                    newPage.pageNumber = before;
                    newPage.pagesAfter.add(after);
                    pages.put(before, newPage);
                }

                if(pages.containsKey(after)) {
                    Page page = pages.get(after);
                    page.pagesBefore.add(before);
                } else {
                    Page newPage = day5.new Page();
                    newPage.pageNumber = after;
                    newPage.pagesBefore.add(before);
                    pages.put(after, newPage);
                }
            } else if(line.contains(",")) {
                List<String> splitLine = Arrays.asList(line.split(","));
                updates.add(splitLine.stream().map(Integer::parseInt).collect(Collectors.toList()));
            }
        }
    }

    static void getResult() {

        int partOneResult = 0;
        int partTwoResult = 0;

        for(List<Integer> update : updates) {

            Boolean updateSafe = true;
            
            checkIfSafe:
            for(int i = 0; i < update.size(); i++) {
                int pageNumber = update.get(i);
                Page pageRules = pages.get(pageNumber);
                if(i > 0) {
                    List<Integer> pagesBefore = update.subList(0, i);
                    if(pageRules.pagesBefore.containsAll(pagesBefore)) {
                    } else {
                        updateSafe = false;
                        break checkIfSafe;
                    }
                }

                if(i < update.size() - 1) {
                    List<Integer> pagesAfter = update.subList(i+1, update.size());
                    if(pageRules.pagesAfter.containsAll(pagesAfter)) {
                    } else {
                        updateSafe = false;
                        break checkIfSafe;
                    }
                }        
            }

            if(updateSafe) { 
                partOneResult += update.get((update.size()-1)/2); 
            } else {
                List<Integer> safeVersion = makeSafe(update);
                partTwoResult += safeVersion.get((safeVersion.size()-1)/2);
            }

        }

        System.out.println("part one: "+partOneResult);
        System.out.println("part two: "+ partTwoResult);

    }

    static List<Integer> makeSafe(List<Integer> input) {

        Integer[] safeArray = new Integer[input.size()];

        ArrayList<Integer> remaining = new ArrayList<Integer>(input);

        for(int i = 0; i < input.size(); i++){
            findAllowedPage:
            for(Integer page : remaining){         
                if(!pages.get(page).pagesBefore.stream().anyMatch(remaining::contains)) {
                    //The pages allowed before this page does NOT contain any of the remaining pages, so put it here!
                    safeArray[i] = page;
                    break findAllowedPage;
                }
            }
            remaining.removeAll(Arrays.asList(safeArray));
        }

        return Arrays.asList(safeArray);
    }
    
    class Page {
        int pageNumber;
        ArrayList<Integer> pagesBefore = new ArrayList<Integer>();
        ArrayList<Integer> pagesAfter = new ArrayList<Integer>();
    }

}
