package edu.eci.ieti.envirify.services;

import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.model.Book;

/**
 * Books Methods For Envirify App.
 *
 * @author Error 418
 */
public interface BookServices {

    /**
     * Adds a New Booking On The App.
     *
     * @param book  The Book Information.
     * @param email The Email Of The User.
     * @throws EnvirifyException When Something Fails.
     */
    void addNewBooking(Book book, String email) throws EnvirifyException;

    /**
     * Deletes A Booking With His Id.
     *
     * @param bookingId The Booking Id.
     * @param email     The User Email.
     * @throws EnvirifyException When Something Fails.
     */
    void deleteBooking(String bookingId, String email) throws EnvirifyException;
}
