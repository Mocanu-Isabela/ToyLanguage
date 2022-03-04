package Exceptions;

public class NonExistentKeyDictionaryException extends DictionaryException{
    private final String message;

    public NonExistentKeyDictionaryException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
