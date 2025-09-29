package net.microgoose.mocknet.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
