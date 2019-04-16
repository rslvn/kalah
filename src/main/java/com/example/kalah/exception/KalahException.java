package com.example.kalah.exception;

/**
 * This exception is created to manage kalah game exceptions
 */
public class KalahException extends RuntimeException {

    /**
     * The constructor
     *
     * @param message the message
     */
    public KalahException(String message) {
        super(message);
    }

    /**
     * the static caller
     *
     * @param messageFormat the messageFormat
     * @param params        the params
     * @return a exception instance
     */
    public static KalahException to(String messageFormat, Object... params) {
        return new KalahException(String.format(messageFormat, params));
    }
}
