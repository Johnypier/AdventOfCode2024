package advent.of.code;

import advent.of.code.util.AdventOfCodeUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DayThree {
    static final List<String> input = AdventOfCodeUtils.readAllFileLines("inputD3.txt");

    public static void main(String[] args) {
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part one: " + DayThree.solutionPartOne());
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part two: " + DayThree.solutionPartTwo());
    }

    static int solutionPartOne() {
        Pattern functionPattern = Pattern.compile("(mul\\(\\d+,\\d+\\))");

        int sum = 0;

        for (String line : input) {
            Matcher functionMatcher = functionPattern.matcher(line);
            while (functionMatcher.find()) {
                sum += mulParser(functionMatcher.group());
            }
        }

        return sum;
    }

    static int solutionPartTwo() {
        Pattern functionsPattern = Pattern.compile("(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don't\\(\\))");

        boolean isNotBlocked = true;

        int sum = 0;

        for (String line : input) {
            Matcher functionsMatcher = functionsPattern.matcher(line);
            while (functionsMatcher.find()) {
                if (functionsMatcher.group().contains("don")) {
                    isNotBlocked = false;
                }
                if (functionsMatcher.group().contains("do(")) {
                    isNotBlocked = true;
                }
                if (isNotBlocked && functionsMatcher.group().contains("m")) {
                    sum += mulParser(functionsMatcher.group());
                }

            }
        }

        return sum;
    }

    static int mulParser(String captureGroup) {
        Pattern numberPattern = Pattern.compile("(\\d+)");

        Matcher numberMatcher = numberPattern.matcher(captureGroup);

        int multiplicationResult = 1;
        while (numberMatcher.find()) {
            multiplicationResult *= Integer.parseInt(numberMatcher.group());
        }

        return multiplicationResult;
    }
}
