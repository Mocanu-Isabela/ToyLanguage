package Exceptions;

public class FullDictionaryException extends DictionaryException{
    private final String message;

    public FullDictionaryException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}