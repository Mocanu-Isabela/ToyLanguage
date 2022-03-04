package Exceptions;

public class FullStackException extends StackException{
    private final String message;

    public FullStackException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

