package main.java.aoc24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import main.java.aoc24.utils.InputFile;

public class Day12 {
    static Day12 day12 = new Day12();

    static final Boolean TEST_MODE = false;

    static String[][] map;
    static int gridSize;
    static HashMap<String, ArrayList<Region>> regionsByType;
    static ArrayList<Region> regions;

    static final Map<String, String> ORIENTATIONS = Map.of(
        "left", "vertical",
        "right", "vertical",
        "above", "horizontal",
        "below", "horizontal"
    );

    public static void main(String[] args) throws Exception {
        
        long startTime = System.nanoTime();
        buildMap();

        regionsByType = new HashMap<String, ArrayList<Region>>();
        regions = new ArrayList<Region>();

        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                String coordinates = j + "," + i;
                String plant = map[i][j];

                if(!plant.equals(".")) {
                    HashSet<String> regionPlots = getMatchingNeighbourPlots(plant, coordinates, new HashSet<String>());
                    regions.add(day12.new Region(plant, regionPlots));
                }
            }
        }

        Long partOneResult = 0L;
        Long partTwoResult = 0L;

        for(Region region : regions) {
            int partOneRegionCost = region.plots.size() * region.boundaries.size();
            int partTwoRegionCost = region.plots.size() * region.fences.size();
            partOneResult += partOneRegionCost;
            partTwoResult += partTwoRegionCost;
        }

        System.out.println("Part one result: " + partOneResult);
        System.out.println("Part two result: " + partTwoResult);

        System.out.println("time taken in milliseconds: " + (System.nanoTime() - startTime) / 1000000 );
    }

    static HashSet<String> getMatchingNeighbourPlots(String type, String coordinates, HashSet<String> neighbours) {
        
        neighbours.add(coordinates);
        
        String[] split = coordinates.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);

        map[y][x] = ".";
        
        String coordinatesTop = x + "," + (y-1);
        String coordinatesBottom = x + "," + (y+1);
        String coordinatesLeft = (x-1) + "," + y;
        String coordinatesRight = (x+1) + "," + y;

        if(y > 0 && map[y-1][x].equals(type)) {
            neighbours.addAll(getMatchingNeighbourPlots(type, coordinatesTop, neighbours));
        }
        if(y < gridSize - 1 && map[y+1][x].equals(type)) {
            neighbours.addAll(getMatchingNeighbourPlots(type, coordinatesBottom, neighbours));
        }
        if(x > 0 && map[y][x-1].equals(type)) {
            neighbours.addAll(getMatchingNeighbourPlots(type, coordinatesLeft, neighbours));
        }
        if(x < gridSize - 1 && map[y][x+1].equals(type)) {
            neighbours.addAll(getMatchingNeighbourPlots(type, coordinatesRight, neighbours));
        }

        //printMap();
        return neighbours;

    }

    static void buildMap() {
        System.out.println("*** Building map... ***");
        List<String> lines = TEST_MODE ? new InputFile("day12").testLines : new InputFile("day12").inputLines;
        gridSize = lines.size();
        map = new String[gridSize][gridSize];
        for(int y = 0; y < gridSize; y++) {
            char[] row = lines.get(y).toCharArray();
            for(int x = 0; x < gridSize; x++) {
                map[y][x] = String.valueOf(row[x]);
            }
        }
    }

    static void printMap() {
        for(String[] row : map) {
            System.out.println(String.join("", Arrays.asList(row)));
        }
    }

    class Region {
        String type;
        HashSet<String> plots;
        ArrayList<String> boundaries;
        ArrayList<Fence> fences;

        Region(String type, HashSet<String> plots) {
            this.type = type;
            this.plots = plots;
            boundaries = new ArrayList<String>();
            fences = new ArrayList<Fence>();
            for(String plot : plots) {
                addPlot(plot);
            }
            fences = combineFences(fences, new ArrayList<Fence>());
        }

        void addPlot(String plotCoordinates) {
            plots.add(plotCoordinates);

            String[] coordinates = plotCoordinates.split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            String upBoundary = x + "," + (y-1) + "~" + plotCoordinates;
            String downBoundary = plotCoordinates + "~" + x + "," + (y+1);
            String leftBoundary = (x-1) + "," + y + "|" + plotCoordinates;
            String rightBoundary = plotCoordinates + "|" + (x+1) + "," + y;

            addBoundary(upBoundary);
            addBoundary(downBoundary);
            addBoundary(leftBoundary);
            addBoundary(rightBoundary);

            //defining boundaries for part 2:
            addFence(x, y, "above");
            addFence(x, y, "below");
            addFence(x, y, "left");
            addFence(x, y, "right");
        }

        void addBoundary(String boundary) {
            if(boundaries.contains(boundary)) {
                boundaries.remove(boundary);
            } else {
                boundaries.add(boundary);
            }
        }

        void addFence(int x, int y, String orientation) {

            int start = ORIENTATIONS.get(orientation).equals("horizontal") ? x : y;
            int end = ORIENTATIONS.get(orientation).equals("horizontal") ? x : y;
            int perpendicular = ORIENTATIONS.get(orientation).equals("horizontal") ? y : x;

            if(orientation == "left" && !plots.contains((x-1) + "," +y) ) {
                Fence newFence = day12.new Fence(ORIENTATIONS.get(orientation), orientation, start, end, perpendicular, 1);
                fences.add(newFence);
            } else if(orientation == "right" && !plots.contains((x+1) + "," +y)) {
                Fence newFence = day12.new Fence(ORIENTATIONS.get(orientation), orientation, start, end, perpendicular, 1);
                fences.add(newFence);
            } else if(orientation == "above" && !plots.contains(x + "," + (y-1))) {
                Fence newFence = day12.new Fence(ORIENTATIONS.get(orientation), orientation, start, end, perpendicular, 1);
                fences.add(newFence);
            }  else if(orientation == "below" && !plots.contains(x + "," + (y+1))) {
                Fence newFence = day12.new Fence(ORIENTATIONS.get(orientation), orientation, start, end, perpendicular, 1);
                fences.add(newFence);
            }

        }

    }

    static ArrayList<Fence> combineFences(ArrayList<Fence> separatingFences, ArrayList<Fence> combinedFences) {
        if(separatingFences.isEmpty()) {
            return combinedFences;
        }
        
        Fence fence = separatingFences.get(0);
        separatingFences.remove(fence);

        Fence combinedFence = day12.new Fence(fence.type, fence.orientation, fence.start, fence.end, fence.perp, fence.length);

        Fence before = separatingFences.stream().filter(
            f -> 
                fence.orientation.equals(f.orientation) && f.perp == fence.perp && f.end == fence.start-1
        ).findAny().orElse(null);
        
        Fence after = separatingFences.stream().filter(
            f -> 
                fence.orientation.equals(f.orientation) && f.perp == fence.perp && f.start == fence.end+1
        ).findAny().orElse(null);

        if(before == null && after == null) {
            //run out of sections to combine
            combinedFences.add(combinedFence);
        } else {
            //found something to add to fence
            if(before != null) {
                separatingFences.remove(before);
                combinedFence.start = before.start;
                combinedFence.length += before.length;   
            }
    
            if(after != null) {
                separatingFences.remove(after);
                combinedFence.end = after.end;
                combinedFence.length += after.length; 
            }

            separatingFences.add(0, combinedFence);
        }

        return combineFences(separatingFences, combinedFences);
    }

    class Fence {
        String type; // vertical or horizontal
        String orientation; // left/right or above/below

        //for vertical fences, start and end are in y-dimension
        //for horizontal fences, start end end are in x-dimension
        int start;
        int end;
        int perp; //the perpendicular coordinate (e.g. for vertical fences the single x-coordinate)
        int length;

        Fence(String type, String orientation, int start, int end, int perp, int len) {
            this.type = type;
            this.orientation = orientation;
            this.start = start;
            this.end = end;
            this.perp = perp;
            this.length = len;
        }
    }
}
