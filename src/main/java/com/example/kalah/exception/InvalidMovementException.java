package com.example.kalah.exception;

/**
 * This exception is created to manage invalid movement attemps
 */
public class InvalidMovementException extends KalahException {

    /**
     * The constructor
     *
     * @param message the message
     */
    public InvalidMovementException(String message) {
        super(message);
    }

    /**
     * the static caller
     *
     * @param messageFormat the messageFormat
     * @param params        the params
     * @return a exception instance
     */
    public static InvalidMovementException to(String messageFormat, Object... params) {
        return new InvalidMovementException(String.format(messageFormat, params));
    }
}
