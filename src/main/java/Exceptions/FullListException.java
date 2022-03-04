package Exceptions;

public class FullListException extends ListException{
    private final String message;

    public FullListException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}