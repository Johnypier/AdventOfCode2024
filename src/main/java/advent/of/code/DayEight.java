package advent.of.code;

import advent.of.code.util.AdventOfCodeUtils;
import advent.of.code.util.Pair;
import advent.of.code.util.Position;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

final class DayEight {
    static final List<char[]> INPUT = AdventOfCodeUtils.readAllFileLines("test.txt")
                                                       .stream()
                                                       .map(String::toCharArray)
                                                       .toList();
    static final Map<Character, HashSet<Position>> MAPPED = new HashMap<>();

    static {
        INPUT.forEach(arr -> {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == '.') {
                    continue;
                }

                if (!MAPPED.containsKey(arr[i])) {
                    MAPPED.put(arr[i], new HashSet<>());
                    MAPPED.get(arr[i]).add(new Position(i, INPUT.indexOf(arr)));
                } else {
                    MAPPED.get(arr[i]).add(new Position(i, INPUT.indexOf(arr)));
                }
            }
        });
    }

    public static void main(String[] args) {
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part one: " + solutionPartOne());
    }

    static int solutionPartOne() {
        AtomicInteger result = new AtomicInteger(0);

        generatePairs().values().forEach(value -> result.getAndAdd(value.size()));

        return result.get() * 2;
    }

    static Map<Character, List<Pair<Position, Position>>> generatePairs() {
        Map<Character, List<Pair<Position, Position>>> pairsMap = new HashMap<>();

        MAPPED.forEach((key, value) -> pairsMap.put(key, new LinkedList<>()));

        for (Entry<Character, List<Pair<Position, Position>>> entry : pairsMap.entrySet()) {
            recursivePairsGenerator(MAPPED.get(entry.getKey()).stream().toList(), entry.getValue(), 0);
        }

        // TODO: Implement pairs validation

        return pairsMap;
    }

    static void recursivePairsGenerator(List<Position> positions,
                                        List<Pair<Position, Position>> current,
                                        int start) {
        if (start == positions.size() - 1) {
            return;
        }

        Position first = positions.get(start);

        for (int i = start + 1; i < positions.size(); i++) {
            current.add(new Pair<>(first, positions.get(i)));
        }

        recursivePairsGenerator(positions, current, start + 1);
    }
}
