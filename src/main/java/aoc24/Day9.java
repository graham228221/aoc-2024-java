package main.java.aoc24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import main.java.aoc24.utils.InputFile;

public class Day9 {

    static Day9 day9 = new Day9();

    static final String FREE_SPACE = ".";
    static int fileSystemSize;

    static HashMap<Integer, Integer> files = new HashMap<Integer, Integer>(); //place index, group id
    static HashMap<Integer, File> filesById = new HashMap<Integer, File>(); //group id, File object
    static ArrayList<Integer> freeSpaces = new ArrayList<Integer>(); //place index
    static HashMap<Integer, Integer> freeSpaceBlockBySize = new HashMap<Integer, Integer>(); //start index, free block size

    public static void main(String[] args) throws Exception {
        //List<String> lines = new InputFile("day9").testLines;
        List<String> lines = new InputFile("day9").inputLines;

        char[] charArray = lines.get(0).toCharArray();
        int fileIndex = 0; //running ID of files
        int placeIndex = 0;

        System.out.println("*** Building new file system view ***");

        for(int i = 0; i < charArray.length; i++) {
            int blockSize = Character.getNumericValue(charArray[i]);
            if(( (i & 1) == 0 )) {
                filesById.put(fileIndex, day9.new File(fileIndex, blockSize, placeIndex)); //for part 2
                for(int j = 0; j < blockSize; j++) {
                    files.put(placeIndex, fileIndex);
                    placeIndex ++;
                }
                fileIndex ++;
            } else {
                freeSpaceBlockBySize.put(placeIndex, blockSize); //for part 2
                for(int j = 0; j < blockSize; j++) {
                    freeSpaces.add(placeIndex);
                    placeIndex++;
                }
            }
        }

        fileSystemSize = placeIndex;

        System.out.println("File system size:  " + fileSystemSize);

        printSystem();

        //runPartOneOptimize();
        runPartTwoOptimize();


    }

    static void runPartOneOptimize() {

        System.out.println("*** Optimizing file system according to Part 1 rules ***");

        for(int i = fileSystemSize - 1; i >= 0; i--) {

            if(files.containsKey(i)) {
                if(freeSpaces.get(0) < i) {
                    files.put(freeSpaces.get(0), files.get(i));
                    files.remove(i);
                    freeSpaces.remove(0);
                    freeSpaces.add(i);
                } else {
                    break;
                }
            }
        }

        System.out.println("Finished optimizing file system...");
        printSystem();

        Long partOneResult = 0L;

        for(Integer i : files.keySet()) {
            partOneResult += i * files.get(i);
        }

        System.out.println("Part one result: " + partOneResult);
    }

    static void runPartTwoOptimize() {

        System.out.println("*** Optimizing file system according to Part 2 rules ***");

        List<Integer> groupIds = new ArrayList<Integer>(filesById.keySet());

        Collections.reverse(groupIds);

        for(int groupId : groupIds) {

            List<Integer> freespaceStartIndexes = new ArrayList<Integer>(freeSpaceBlockBySize.keySet());

            Collections.sort(freespaceStartIndexes);

            for(int freeIndex : freespaceStartIndexes) {

                if(freeIndex < filesById.get(groupId).start) {

                    if(freeSpaceBlockBySize.get(freeIndex) >= filesById.get(groupId).size) {

                        for(int i = freeIndex; i < freeIndex + filesById.get(groupId).size; i++) {
                            files.put(i, groupId);
                            freeSpaces.remove(Integer.valueOf(i));
                        }

                        for(int i = filesById.get(groupId).start; i < filesById.get(groupId).end; i++) {
                            files.remove(Integer.valueOf(i));
                            freeSpaces.add(Integer.valueOf(i));
                        }

                        if(freeSpaceBlockBySize.get(freeIndex) > filesById.get(groupId).size){
                            freeSpaceBlockBySize.put(freeIndex + filesById.get(groupId).size, freeSpaceBlockBySize.get(freeIndex) - filesById.get(groupId).size);
                        }

                        freeSpaceBlockBySize.remove(Integer.valueOf(freeIndex));
                        break;
                    }

                } else {
                    break;
                }
            }
        }

        System.out.println("Finished optimizing file system...");

        Long partTwoResult = 0L;

        for(Integer i : files.keySet()) {
            partTwoResult += i * files.get(i);
        }

        System.out.println("Part two result: " + partTwoResult);
    }

    static void printSystem() {
        for(int i = 0; i < fileSystemSize; i++) {
            if(files.containsKey(i)) {
                if(files.get(i) < 10){
                    System.out.print(files.get(i));
                } else {
                    System.out.print("#");
                }
                
            } else {
                System.out.print(FREE_SPACE);
            }
        }
        System.out.println("$");
    }

    class File {
        int id;
        int size;
        int start;
        int end;

        File(int id, int size, int startIndex) {
            this.id = id;
            this.size = size;
            this.start = startIndex;
            this.end = start + size;
        }
    }


}
