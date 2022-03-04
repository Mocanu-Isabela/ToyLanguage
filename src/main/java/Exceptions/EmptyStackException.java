package Exceptions;

public class EmptyStackException extends StackException{
    private final String message;

    public EmptyStackException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
