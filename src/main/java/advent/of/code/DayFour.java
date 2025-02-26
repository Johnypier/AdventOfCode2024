package advent.of.code;

import advent.of.code.util.AdventOfCodeUtils;
import advent.of.code.util.Direction;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DayFour {
    static final List<char[]> INPUT = AdventOfCodeUtils.readAllFileLines("inputD4.txt")
                                                       .stream()
                                                       .map(String::toCharArray)
                                                       .toList();
    static final Map<Character, Character> LETTERS_MAPPING = new HashMap<>();
    static final char X = 'X';
    static final char M = 'M';
    static final char A = 'A';
    static final char S = 'S';
    static final int LINE_LENGTH;
    static int wordsCount = 0;

    static {
        LINE_LENGTH = INPUT.get(0).length - 1;
        LETTERS_MAPPING.put(M, A);
        LETTERS_MAPPING.put(A, S);
    }

    public static void main(String[] args) {
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part one: " + DayFour.solutionPartOne());
        wordsCount = 0; // Reset shared attribute
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part two: " + DayFour.solutionPartTwo());
    }

    static int solutionPartOne() {
        for (int i = 0; i < INPUT.size(); i++) {
            char[] lineCharacters = INPUT.get(i);

            for (int j = 0; j < lineCharacters.length; j++) {
                if (lineCharacters[j] == X) {
                    wordsExists(i, j);
                }
            }
        }

        return wordsCount;
    }

    static int solutionPartTwo() {
        for (int i = 0; i < INPUT.size(); i++) {
            char[] lineCharacters = INPUT.get(i);

            for (int j = 0; j < lineCharacters.length; j++) {
                if (lineCharacters[j] == A) {
                    xMASExists(i, j);
                }
            }
        }

        return wordsCount;
    }

    static void wordsExists(int lineIndex, int charIndex) {
        // Upper search
        directionSearchUniversal(lineIndex != 0 && charIndex != 0, Direction.UPPER_LEFT, lineIndex - 1, charIndex - 1,
                                 M);
        directionSearchUniversal(lineIndex != 0, Direction.UP, lineIndex - 1, charIndex, M);
        directionSearchUniversal(lineIndex != 0 && charIndex != LINE_LENGTH, Direction.UPPER_RIGHT,
                                 lineIndex - 1, charIndex + 1, M);

        // Middle search
        directionSearchUniversal(charIndex != 0, Direction.LEFT, lineIndex, charIndex - 1, M);
        directionSearchUniversal(charIndex != LINE_LENGTH, Direction.RIGHT, lineIndex, charIndex + 1, M);

        // Bottom search
        directionSearchUniversal(lineIndex != INPUT.size() - 1 && charIndex != 0, Direction.BOTTOM_LEFT, lineIndex + 1,
                                 charIndex - 1, M);
        directionSearchUniversal(lineIndex != INPUT.size() - 1, Direction.BOTTOM, lineIndex + 1, charIndex, M);
        directionSearchUniversal(lineIndex != INPUT.size() - 1 && charIndex != LINE_LENGTH,
                                 Direction.BOTTOM_RIGHT, lineIndex + 1, charIndex + 1, M);
    }


    static void directionSearch(Direction direction, int lineIndex, int charIndex, char charToFind) {
        switch (direction) {
            case UP -> directionSearchUniversal(lineIndex != 0, Direction.UP, lineIndex - 1, charIndex, charToFind);
            case UPPER_LEFT ->
                    directionSearchUniversal(lineIndex != 0 && charIndex != 0, Direction.UPPER_LEFT, lineIndex - 1,
                                             charIndex - 1, charToFind);
            case UPPER_RIGHT -> directionSearchUniversal(lineIndex != 0 && charIndex != LINE_LENGTH,
                                                         Direction.UPPER_RIGHT, lineIndex - 1, charIndex + 1,
                                                         charToFind);
            case LEFT -> directionSearchUniversal(charIndex != 0, Direction.LEFT, lineIndex, charIndex - 1, charToFind);
            case RIGHT -> directionSearchUniversal(charIndex != LINE_LENGTH, Direction.RIGHT, lineIndex,
                                                   charIndex + 1, charToFind);
            case BOTTOM ->
                    directionSearchUniversal(lineIndex != INPUT.size() - 1, Direction.BOTTOM, lineIndex + 1, charIndex,
                                             charToFind);
            case BOTTOM_LEFT ->
                    directionSearchUniversal(lineIndex != INPUT.size() - 1 && charIndex != 0, Direction.BOTTOM_LEFT,
                                             lineIndex + 1, charIndex - 1, charToFind);
            case BOTTOM_RIGHT ->
                    directionSearchUniversal(lineIndex != INPUT.size() - 1 && charIndex != INPUT.get(0).length - 1,
                                             Direction.BOTTOM_RIGHT, lineIndex + 1, charIndex + 1, charToFind);
            default -> throw new InvalidParameterException("Unsupported direction detected!");
        }
    }

    static void directionSearchUniversal(boolean mainCondition, Direction direction,
                                         int lineIndex, int charIndex, char charToFind) {
        if (mainCondition) {
            char[] line = INPUT.get(lineIndex);

            if (line[charIndex] == charToFind) {
                if (charToFind == S) {
                    wordsCount++;
                    return;
                }
                directionSearch(direction, lineIndex, charIndex, LETTERS_MAPPING.get(charToFind));
            }
        }
    }

    static void xMASExists(int lineIndex, int charIndex) {
        if (lineIndex != 0 && charIndex != 0 && charIndex != LINE_LENGTH && lineIndex != INPUT.size() - 1) {
            char[] upperLine = INPUT.get(lineIndex - 1);
            char[] bottomLine = INPUT.get(lineIndex + 1);

            boolean c1 = upperLine[charIndex - 1] == M && bottomLine[charIndex + 1] == S;
            boolean c2 = upperLine[charIndex - 1] == S && bottomLine[charIndex + 1] == M;
            boolean c3 = upperLine[charIndex + 1] == M && bottomLine[charIndex - 1] == S;
            boolean c4 = upperLine[charIndex + 1] == S && bottomLine[charIndex - 1] == M;

            if ((c1 || c2) && (c3 || c4)) {
                wordsCount++;
            }
        }
    }
}
