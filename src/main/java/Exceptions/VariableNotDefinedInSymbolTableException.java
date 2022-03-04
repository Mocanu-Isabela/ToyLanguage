package Exceptions;

public class VariableNotDefinedInSymbolTableException extends Exception {
    private final String message;

    public VariableNotDefinedInSymbolTableException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
