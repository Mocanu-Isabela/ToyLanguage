package Exceptions;

public class TypeException extends Exception{
    private final String message;

    public TypeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
