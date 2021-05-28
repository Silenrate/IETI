package edu.eci.ieti.envirify.exceptions;

/**
 * Personalized Exception For Envirify Persistence Errors.
 *
 * @author Error 418
 */
public class EnvirifyPersistenceException extends Exception {

    public static final String DATE_INTERVAL_ERROR = "That Date Interval Is Already Occupied";

    /**
     * Basic Constructor For EnvirifyPersistenceException.
     *
     * @param msg The Error Message Of The Exception.
     */
    public EnvirifyPersistenceException(String msg) {
        super(msg);
    }
}
