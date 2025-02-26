package advent.of.code.util;

import java.util.*;

@SuppressWarnings("unused")
public final class CombinatoricsUtils {
    private CombinatoricsUtils() {
        // Block
    }

    public static List<List<Integer>> generateCombinations(List<Integer> list, int size) {
        List<List<Integer>> result = new ArrayList<>();
        generateCombinationsHelper(list, new ArrayList<>(), 0, result, size > 0, size);
        return result;
    }

    private static void generateCombinationsHelper(List<Integer> list, List<Integer> current, int start,
                                                   List<List<Integer>> result, boolean isOfSize, int size) {

        if (isOfSize) {
            if (current.size() == size) {
                result.add(new ArrayList<>(current)); // Add the current combination to the result
                return;
            }
        } else {
            result.add(new ArrayList<>(current)); // Add the current combination to the result
        }

        // Generate combinations by adding one element at a time
        for (int i = start; i < list.size(); i++) {
            current.add(list.get(i)); // Include the current element
            generateCombinationsHelper(list, current, i + 1, result, isOfSize, size); // Recur for the next elements
            current.remove(current.size() - 1); // Backtrack (remove the last element)
        }
    }

    public static List<List<Integer>> generatePermutations(List<Integer> list) {
        List<List<Integer>> result = new ArrayList<>();
        generatePermutationsHelper(list, 0, result);
        return result;
    }

    private static void generatePermutationsHelper(List<Integer> list, int start, List<List<Integer>> result) {
        if (start == list.size() - 1) {
            result.add(new ArrayList<>(list)); // Add the current permutation
            return;
        }

        for (int i = start; i < list.size(); i++) {
            // Skip swapping an element with itself
            if (i != start && list.get(i).equals(list.get(start))) {
                continue;
            }

            swap(list, start, i); // Swap the current element with the start element
            generatePermutationsHelper(list, start + 1, result); // Recur for the next elements
            swap(list, start, i); // Backtrack (swap back)
        }
    }

    private static void swap(List<Integer> list, int i, int j) {
        if (i == j) {
            return; // Skip swapping an element with itself
        }
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static List<List<Integer>> generateUniquePermutations(int[] elements, int length) {
        List<List<Integer>> result = new ArrayList<>();
        generateUniquePermutations(elements, length, new ArrayList<>(), result);
        return result;
    }

    private static void generateUniquePermutations(int[] elements, int length, List<Integer> current,
                                                   List<List<Integer>> result) {
        // If the current variation is complete, add it to the result
        if (current.size() == length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Use a set to track used elements in the current position to avoid duplicates
        Set<Integer> used = new HashSet<>();
        for (int element : elements) {
            // Skip if the element has already been used in this position
            if (used.contains(element)) {
                continue;
            }

            // Mark the element as used for this position
            used.add(element);

            // Add the current element to the variation
            current.add(element);

            // Recur for the next position
            generateUniquePermutations(elements, length, current, result);

            // Backtrack (remove the last element)
            current.remove(current.size() - 1);
        }
    }
}
