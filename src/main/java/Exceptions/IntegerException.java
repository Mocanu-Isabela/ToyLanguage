package Exceptions;

public class IntegerException extends Exception {
    private final String message;

    public IntegerException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
