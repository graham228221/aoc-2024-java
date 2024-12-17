package main.java.aoc24;

import java.util.ArrayList;
import java.util.List;

import main.java.aoc24.utils.InputFile;

public class Day13 {
    
    static Day13 day13 = new Day13();
    static final Boolean TEST_MODE = false;

    static final Boolean PART_TWO = true;
    
    static ArrayList<Machine> machines = new ArrayList<Machine>();
    
    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        System.out.println("Parsing input...");
        parseInput();

        long result = 0L;

        for(Machine machine : machines) {
            result += machine.solve();
        }

        System.out.println("Result: " + result);
        System.out.println("time taken in milliseconds: " + (System.nanoTime() - startTime) / 1000000 );
    }

    static void parseInput() {
        List<String> lines = TEST_MODE ? new InputFile("day13").testLines : new InputFile("day13").inputLines;

        Tuple buttonA = day13.new Tuple(1, 1);
        Tuple buttonB = day13.new Tuple(1, 1);
        Tuple prizeLocation;

        for(int i = 0; i < lines.size(); i++) {
            
            String line = lines.get(i);

            if(line.startsWith("Button A")) {
                buttonA = getTupleFromInputString(line);
            } else if(line.startsWith("Button B")) {
                buttonB = getTupleFromInputString(line);
            } else if(line.startsWith("Prize")) {
                prizeLocation = getTupleFromInputString(line);
                
                Machine clawMachine = day13.new Machine();
                clawMachine.buttonA = buttonA;
                clawMachine.buttonB = buttonB;
                clawMachine.prizeLocation = prizeLocation;

                machines.add(clawMachine);
            }
        }
    }

    static Tuple getTupleFromInputString(String input) {
        String[] numbers = input.split(": ")[1].split(", ");

        double x = Integer.parseInt(numbers[0].replace("X+", "").replace("X=", ""));
        double y = Integer.parseInt(numbers[1].replace("Y+", "").replace("Y=", ""));

        return day13.new Tuple(x, y);
    }

    public class Machine {

        Tuple buttonA;
        Tuple buttonB;
        Tuple prizeLocation;

        Long solve(){

            Long prizeLocationX = PART_TWO ? Double.valueOf(prizeLocation.x).longValue() + 10000000000000L : Double.valueOf(prizeLocation.x).longValue();
            Long prizeLocationY = PART_TWO ? Double.valueOf(prizeLocation.y).longValue() + 10000000000000L : Double.valueOf(prizeLocation.y).longValue();

            Long b = Double.valueOf(((buttonA.y * prizeLocationX) - (prizeLocationY * buttonA.x))/(-(buttonA.x * buttonB.y) + (buttonA.y * buttonB.x))).longValue();
            double doubleB = ((buttonA.y * prizeLocationX) - (prizeLocationY * buttonA.x))/(-(buttonA.x * buttonB.y) + (buttonA.y * buttonB.x));

            Long a = Double.valueOf((prizeLocationX - (buttonB.x * b) ) / buttonA.x).longValue();
            double doubleA = Double.valueOf((prizeLocationX - (buttonB.x * b) ) / buttonA.x).longValue();

            // if the Long solution is equal to the double solution, it should be a whole number
            Boolean validA = (a <= 100 || PART_TWO) && doubleA == a;
            Boolean validB = (b <= 100 || PART_TWO) && doubleB == b;

            return validA && validB ? (a * 3) + (b * 1) : 0;
        }

    }

    class Tuple {
        double x;
        double y;

        Tuple(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
