package advent.of.code;

import advent.of.code.util.AdventOfCodeUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

final class DayTwo {
    static final Map<List<Integer>, Boolean> input = new HashMap<>();

    static {
        AdventOfCodeUtils.readAllFileLines("inputD2.txt").stream().map(AdventOfCodeUtils::getIntegerValuesAsList)
                         .forEach(list -> input.put(list, false));
    }

    public static void main(String[] args) {
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part one: " + DayTwo.solutionPartOne());
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part two: " + DayTwo.solutionPartTwo());
    }

    static int solutionPartOne() {
        AtomicInteger safeNumber = new AtomicInteger(0);

        input.keySet().forEach(list -> {
            if (isSafe(list)) {
                safeNumber.set(safeNumber.get() + 1);
            }
        });

        return safeNumber.get();
    }

    static int solutionPartTwo() {
        AtomicInteger safeNumber = new AtomicInteger(0);

        input.keySet().forEach(list -> {
            if (isSafe(list)) {
                safeNumber.set(safeNumber.get() + 1);
            } else {
                if (checkByElimination(list)) {
                    safeNumber.set(safeNumber.get() + 1);
                }
            }
        });

        return safeNumber.get();
    }

    // Remove the problematic value once and check if the new list is safe.
    static boolean checkByElimination(List<Integer> originalList) {
        // Check if at least one version of the new list is safe, if yes, then return true.
        for (int i = 0; i < originalList.size(); i++) {
            List<Integer> modifiedCopy = new LinkedList<>(List.copyOf(originalList));
            modifiedCopy.remove(i);
            if (isSafe(modifiedCopy)) {
                return true;
            }
        }

        return false;
    }

    static boolean isSafe(List<Integer> line) {
        if (line.size() < 2) {
            return true;
        }

        boolean dec = line.get(0) > line.get(1);

        for (int i = 0; i < line.size() - 1; i++) {
            int a = line.get(i);
            int b = line.get(i + 1);

            boolean cond;
            if (dec) {
                cond = 1 <= a - b && a - b <= 3;
            } else {
                cond = 1 <= b - a && b - a <= 3;
            }

            if (!cond) {
                return false;
            }
        }
        return true;
    }
}
