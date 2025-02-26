package advent.of.code;

import advent.of.code.util.AdventOfCodeUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

final class DayFive {
    static final Map<Integer, LinkedList<Integer>> RULES = new HashMap<>();
    static final List<List<Integer>> PAGES = new ArrayList<>();

    static {
        AtomicBoolean isEmptyLineNotFound = new AtomicBoolean(true);

        AdventOfCodeUtils.readAllFileLines("inputD5.txt").forEach(line -> {
            if (isEmptyLineNotFound.get()) {

                if (line.isEmpty()) {
                    isEmptyLineNotFound.set(false);
                    return;
                }

                String[] split = line.split("\\|");
                int n1 = Integer.parseInt(split[0]);
                int n2 = Integer.parseInt(split[1]);

                if (RULES.containsKey(n1)) {
                    RULES.get(n1).add(n2);
                } else {
                    RULES.put(n1, new LinkedList<>(java.util.List.of(n2)));
                }
            } else {
                PAGES.add(new LinkedList<>(Arrays.stream(line.split(",")).map(Integer::parseInt).toList()));
            }
        });
    }

    public static void main(String[] args) {
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part one: " + DayFive.solutionPartOne());
        AdventOfCodeUtils.AOC_LOGGER.info(() -> "Part two: " + DayFive.solutionPartTwo());
    }

    static int solutionPartOne() {
        int sum = 0;

        for (List<Integer> page : PAGES) {
            AtomicBoolean isValid = new AtomicBoolean(true);

            RULES.keySet().forEach(key -> {
                if (isValid.get()) {
                    List<Integer> updates = RULES.get(key);

                    if (page.contains(key)) {
                        int keyIndex = -1;

                        for (int i = 0; i < page.size(); i++) {
                            if (page.get(i) == key.intValue()) {
                                keyIndex = i;
                            }

                            if (keyIndex != -1) {
                                isValid.set(validUpdatesCheck(keyIndex, page, updates));
                                break;
                            }
                        }
                    }
                }
            });

            if (isValid.get()) {
                sum += page.get(page.size() / 2);
            }
        }

        return sum;
    }

    static boolean validUpdatesCheck(int keyIndex, List<Integer> page, List<Integer> updates) {
        if (keyIndex > 0) {
            for (int i = keyIndex - 1; i != -1; i--) {
                if (updates.contains(page.get(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    static int solutionPartTwo() {
        int sum = 0;

        for (List<Integer> page : PAGES) {
            AtomicBoolean isValid = new AtomicBoolean(true);

            RULES.keySet().forEach(key -> {
                if (isValid.get()) {
                    List<Integer> updates = RULES.get(key);

                    if (page.contains(key)) {
                        int keyIndex = -1;

                        for (int i = 0; i < page.size(); i++) {
                            if (page.get(i) == key.intValue()) {
                                keyIndex = i;
                            }

                            if (keyIndex != -1) {
                                isValid.set(validUpdatesCheck(keyIndex, page, updates));
                                break;
                            }
                        }
                    }
                }
            });

            if (!isValid.get() && pageFixer(page)) {
                sum += page.get(page.size() / 2);
            }
        }

        return sum;
    }

    static boolean pageFixer(List<Integer> page) {
        AtomicBoolean isFixed = new AtomicBoolean(false);

        RULES.keySet().forEach(key -> {
            List<Integer> updates = RULES.get(key);
            int originalKeyIndex = page.indexOf(key);

            for (int i = originalKeyIndex; i != -1; i--) {
                if (updates.contains(page.get(i))) {
                    int temp = page.get(i);
                    page.set(i, key);
                    page.set(originalKeyIndex, temp);
                    // Break and repeat the check recursively.
                    isFixed.set(pageFixer(page));
                    break;
                }

                if (i == 0 && !updates.contains(page.get(0))) {
                    isFixed.set(true);
                }
            }
        });

        return isFixed.get();
    }
}
