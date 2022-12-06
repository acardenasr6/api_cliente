package acardenas.com.exception;

public class ValidationException extends Exception{

    public ValidationException() {}

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Exception e) {
        super(message,e);
    }


}
