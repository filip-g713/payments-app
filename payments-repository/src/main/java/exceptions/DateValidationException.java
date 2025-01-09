package exceptions;

public class DateValidationException extends Exception {

    public DateValidationException(String message) {
        super(message);
    }

    public DateValidationException() {}
}
