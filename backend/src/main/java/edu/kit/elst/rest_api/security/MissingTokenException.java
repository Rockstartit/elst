package edu.kit.elst.rest_api.security;

public class MissingTokenException extends RuntimeException {
    public MissingTokenException(String message) {
        super(message);
    }

    public MissingTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
