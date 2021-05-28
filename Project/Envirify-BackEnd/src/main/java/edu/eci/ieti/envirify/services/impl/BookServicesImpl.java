package edu.eci.ieti.envirify.services.impl;

import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Book;
import edu.eci.ieti.envirify.persistence.BookPersistence;
import edu.eci.ieti.envirify.persistence.PlacePersistence;
import edu.eci.ieti.envirify.persistence.UserPersistence;
import edu.eci.ieti.envirify.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Books Implemented Methods For Envirify App.
 *
 * @author Error 418
 */
@Service
public class BookServicesImpl implements BookServices {

    @Autowired
    private BookPersistence persistence;

    @Autowired
    private PlacePersistence placePersistence;

    @Autowired
    private UserPersistence userPersistence;

    /**
     * Adds a New Booking On The App.
     *
     * @param book  The Book Information.
     * @param email The Email Of The User.
     * @throws EnvirifyException When Something Fails.
     */
    @Override
    public void addNewBooking(Book book, String email) throws EnvirifyException {
        Date actualDate = new Date();
        if (book.getInitialDate().before(actualDate)) {
            throw new EnvirifyException("The Booking can not be made for a date prior to today's date", HttpStatus.CONFLICT);
        }
        if (book.getInitialDate().after(book.getFinalDate())) {
            throw new EnvirifyException("The End date of the booking must be after the starting date", HttpStatus.CONFLICT);
        }
        if (book.getPlaceId() == null) {
            throw new EnvirifyException("The booking has to specify the place Id", HttpStatus.BAD_REQUEST);
        }
        try {
            persistence.addNewBooking(book, email);
        } catch (EnvirifyPersistenceException e) {
            if (e.getMessage().equals(EnvirifyPersistenceException.DATE_INTERVAL_ERROR)) {
                throw new EnvirifyException(e.getMessage(), e, HttpStatus.CONFLICT);
            }
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes A Booking With His Id.
     *
     * @param bookingId The Booking Id.
     * @param email     The User Email.
     * @throws EnvirifyException When Something Fails.
     */
    @Override
    public void deleteBooking(String bookingId, String email) throws EnvirifyException {
        try {
            persistence.deleteBooking(bookingId, email);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
    }
}
