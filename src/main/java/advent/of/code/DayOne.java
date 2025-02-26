package advent.of.code;

import advent.of.code.util.AdventOfCodeUtils;

import java.util.ArrayList;
import java.util.List;

final class DayOne {
    static final List<Integer> left;
    static final List<Integer> right;

    static {
        final List<Integer> leftFinal = new ArrayList<>();
        final List<Integer> rightFinal = new ArrayList<>();
        AdventOfCodeUtils.readAllFileLines("inputD1.txt")
                         .forEach(line -> {
                             String[] splitResult = line.split(" {3}");
                             leftFinal.add(Integer.valueOf(splitResult[0]));
                             rightFinal.add(Integer.valueOf(splitResult[1]));
                         });

        left = new ArrayList<>(leftFinal.stream().sorted().toList());
        right = new ArrayList<>(rightFinal.stream().sorted().toList());
    }

    public static void main(String[] args) {
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part one: " + DayOne.solutionPartOne());
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part two: " + DayOne.solutionPartTwo());
    }

    static int solutionPartOne() {
        int result = 0;

        for (int i = 0; i < left.size(); i++) {
            result += Math.abs(left.get(i) - right.get(i));
        }

        return result;
    }

    static int solutionPartTwo() {
        return left.stream()
                   .map(i -> i * (int) right.stream().filter(j -> j.intValue() == i.intValue()).count())
                   .mapToInt(Integer::intValue)
                   .sum();
    }
}
