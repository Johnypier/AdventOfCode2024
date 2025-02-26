package advent.of.code.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AdventOfCodeUtils {
    public static final Logger AOC_LOGGER = Logger.getLogger("AOC");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");

    private AdventOfCodeUtils() {
        // Block
    }

    public static List<String> readAllFileLines(String fileName) {
        try {
            return Files.readAllLines(
                                Path.of(Objects.requireNonNull(AdventOfCodeUtils.class.getClassLoader().getResource(fileName)).toURI()))
                        .stream()
                        .toList();
        } catch (IOException | URISyntaxException e) {
            throw new InputFileProcessingException();
        }
    }

    public static List<Integer> getIntegerValuesAsList(String line) {
        Matcher matcher = NUMBER_PATTERN.matcher(line);
        List<Integer> lineValues = new ArrayList<>();

        while (matcher.find()) {
            lineValues.add(
                    Integer.parseInt(matcher.group()));
        }

        return lineValues;
    }

    public static List<Long> getLongValuesAsList(String line) {
        Matcher matcher = NUMBER_PATTERN.matcher(line);
        List<Long> lineValues = new ArrayList<>();

        while (matcher.find()) {
            lineValues.add(
                    Long.parseLong(matcher.group()));
        }

        return lineValues;
    }
}
