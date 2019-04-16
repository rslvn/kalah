package com.example.kalah.util;

import com.example.kalah.exception.InvalidArgumentException;
import com.example.kalah.exception.InvalidMovementException;
import org.springframework.util.StringUtils;

/**
 * This class is created to manage the parameter or status checking
 */
public class Preconditions {

    private Preconditions() {
        // for sonarqube
    }

    /**
     * checks reference and throw InvalidMovementException
     *
     * @param reference   the reference
     * @param errorFormat the errorMessage
     * @param parameters  the error message parameters
     */
    public static void checkMovement(boolean reference, String errorFormat, Object... parameters) {
        if (!reference) {
            throw InvalidMovementException.to(errorFormat, parameters);
        }
    }

    /**
     * check the reference and throw InvalidArgumentException
     *
     * @param reference   the reference
     * @param errorFormat the errorMessage
     * @param parameters  the error message parameters
     * @param <T>         the generic thype to check the null situation of the object
     */
    public static <T> void checkNotNull(T reference, String errorFormat, Object... parameters) {
        if (reference == null) {
            throw InvalidArgumentException.to(errorFormat, parameters);
        }
    }

    /**
     * checks the reference and throws InvalidArgumentException
     *
     * @param reference   the reference
     * @param errorFormat the errorMessage
     * @param parameters  the error message parameters
     */
    public static void checkNotEmpty(String reference, String errorFormat, Object... parameters) {
        if (!StringUtils.hasText(reference)) {
            throw InvalidArgumentException.to(errorFormat, parameters);
        }
    }

}
