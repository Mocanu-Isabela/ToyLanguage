package Exceptions;

public class EmptyListException extends ListException{
    private final String message;

    public EmptyListException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
