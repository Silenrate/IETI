package edu.eci.ieti.envirify.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Personalized Exception For Envirify App.
 *
 * @author Error 418
 */
public class EnvirifyException extends Exception {

    private final HttpStatus status;

    /**
     * Constructor For EnvirifyException.
     *
     * @param message The Error Message Of The Exception.
     * @param cause   The Cause Of The Error.
     * @param status  The HTTP Status Code Of The Error.
     */
    public EnvirifyException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    /**
     * Constructor For EnvirifyException.
     *
     * @param message The Error Message Of The Exception.
     * @param status  The HTTP Status Code Of The Error.
     */
    public EnvirifyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Returns the HTTP Status Code Of The Exception.
     *
     * @return The HTTP Status Code Of The Exception.
     */
    public HttpStatus getStatus() {
        return status;
    }
}
