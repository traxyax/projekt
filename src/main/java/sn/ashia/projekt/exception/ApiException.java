package sn.ashia.projekt.exception;

public class ApiException extends Exception {
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Exception cause) {
        super(cause);
    }

    public ApiException(String message, Exception cause) {
        super(message, cause);
    }
}
