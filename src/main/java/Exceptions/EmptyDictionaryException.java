package Exceptions;

public class EmptyDictionaryException extends DictionaryException{
    private final String message;

    public EmptyDictionaryException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}