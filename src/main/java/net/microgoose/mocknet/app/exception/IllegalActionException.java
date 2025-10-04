package net.microgoose.mocknet.app.exception;

public class IllegalActionException extends RuntimeException {
    public IllegalActionException(final String message) {
        super(message);
    }

    public IllegalActionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}