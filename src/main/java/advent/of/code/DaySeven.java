package advent.of.code;

import advent.of.code.util.AdventOfCodeUtils;
import advent.of.code.util.CombinatoricsUtils;

import java.util.*;

final class DaySeven {
    static final List<List<Long>> input = AdventOfCodeUtils.readAllFileLines("inputD7.txt")
                                                           .stream()
                                                           .map(AdventOfCodeUtils::getLongValuesAsList)
                                                           .toList();

    public static void main(String[] args) {
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part one: " + DaySeven.solutionPartOne());
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part two: " + DaySeven.solutionPartTwo());
    }

    static long solutionPartOne() {
        long sum = 0;

        for (List<Long> values : input) {
            sum += calibration(values, false);
        }

        return sum;
    }

    static long solutionPartTwo() {
        long sum = 0;

        for (List<Long> values : input) {
            sum += calibration(values, true);
        }

        return sum;
    }

    static long calibration(List<Long> values, boolean isPartTwo) {
        for (List<Integer> variant : CombinatoricsUtils.generateUniquePermutations(
                isPartTwo ? new int[]{0, 1, 2} : new int[]{0, 1}, values.size() - 2)) {
            if (processOperations(values, variant) == values.get(0)) {
                return values.get(0);
            }
        }

        return 0;
    }

    static long processOperations(List<Long> values, List<Integer> operations) {
        long sum = values.get(1);

        for (int i = 2; i < values.size(); i++) {
            if (operations.get(i - 2) == 0) {
                sum += values.get(i);
            } else {
                if (operations.get(i - 2) == 1) {
                    sum *= values.get(i);
                } else {
                    sum = Long.parseLong(sum + String.valueOf(values.get(i)));
                }
            }
        }

        return sum == values.get(0) ? values.get(0) : 0;
    }
}
