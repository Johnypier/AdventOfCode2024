package advent.of.code.util;

public final class InputFileProcessingException extends RuntimeException {
    public InputFileProcessingException() {
        super("Could not process the input file!\nMake sure that it exists and is in the correct directory.");
    }
}