package com.example.kalah.exception;

/**
 * This exception is created to manage invalid arguments situations
 */
public class InvalidArgumentException extends KalahException {

    /**
     * The constructor
     *
     * @param message the message
     */
    public InvalidArgumentException(String message) {
        super(message);
    }

    /**
     * The static caller
     *
     * @param messageFormat the messageFormat
     * @param params        the params
     * @return a exception instance
     */
    public static InvalidArgumentException to(String messageFormat, Object... params) {
        return new InvalidArgumentException(String.format(messageFormat, params));
    }
}
