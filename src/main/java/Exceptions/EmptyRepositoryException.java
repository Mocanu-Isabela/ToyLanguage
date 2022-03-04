package Exceptions;

public class EmptyRepositoryException extends RepositoryException {
    private final String message;

    public EmptyRepositoryException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
