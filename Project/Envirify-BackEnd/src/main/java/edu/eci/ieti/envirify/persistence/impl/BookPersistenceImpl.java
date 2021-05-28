package edu.eci.ieti.envirify.persistence.impl;

import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Book;
import edu.eci.ieti.envirify.model.Place;
import edu.eci.ieti.envirify.model.User;
import edu.eci.ieti.envirify.persistence.BookPersistence;
import edu.eci.ieti.envirify.persistence.repositories.BookRepository;
import edu.eci.ieti.envirify.persistence.repositories.PlaceRepository;
import edu.eci.ieti.envirify.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Books Persistence Implemented Methods For Envirify App.
 *
 * @author Error 418
 */
@Service
public class BookPersistenceImpl implements BookPersistence {

    @Autowired
    private BookRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    /**
     * Adds a New Booking On The DB.
     *
     * @param book  The Book Information.
     * @param email The User Email
     * @throws EnvirifyPersistenceException When Something Fails.
     */
    @Override
    public void addNewBooking(Book book, String email) throws EnvirifyPersistenceException {
        Place place;
        User user = userRepository.findByEmail(email);
        if (user == null) throw new EnvirifyPersistenceException("There is no user with the email address " + email);
        String placeId = book.getPlaceId();
        Optional<Place> placeOptional = placeRepository.findById(placeId);
        if (!placeOptional.isPresent())
            throw new EnvirifyPersistenceException("There is no place with the id " + placeId);
        place = placeOptional.get();
        validateBookDates(place.getBookings(), book.getInitialDate(), book.getFinalDate());
        book.setUserId(user.getId());
        Book registeredBook = repository.save(book);
        user.addBook(registeredBook.getId());
        place.addBook(registeredBook.getId());
        userRepository.save(user);
        placeRepository.save(place);
    }

    /**
     * Deletes A Booking With His Id From DB.
     *
     * @param bookingId The Booking Id.
     * @param email     The User Email.
     * @throws EnvirifyPersistenceException When Something Fails.
     */
    @Override
    public void deleteBooking(String bookingId, String email) throws EnvirifyPersistenceException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new EnvirifyPersistenceException("There is no user with the email address " + email);
        List<String> bookingsIds = user.getBooks();
        if (bookingsIds.isEmpty()) {
            throw new EnvirifyPersistenceException("This user do not have bookings");
        }
        if (!bookingsIds.contains(bookingId)) {
            throw new EnvirifyPersistenceException("This user do not have a booking with the id " + bookingId);
        }
        Book book;
        Optional<Book> bookOptional = repository.findById(bookingId);
        if (!bookOptional.isPresent()) {
            throw new EnvirifyPersistenceException("There is no booking with the id " + bookingId);
        }
        Place place;
        book = bookOptional.get();
        Optional<Place> optionalPlace = placeRepository.findById(book.getPlaceId());
        if (!optionalPlace.isPresent()) {
            throw new EnvirifyPersistenceException("There is no place with the id " + book.getPlaceId());
        }
        place = optionalPlace.get();
        bookingsIds.remove(bookingId);
        place.removeBooking(bookingId);
        repository.deleteById(bookingId);
        userRepository.save(user);
        placeRepository.save(place);
    }

    /**
     * Validate If The Dates Of The New Book Do Not Cause Conflict With Others.
     *
     * @param books       The List Of The Place Books.
     * @param initialDate The Initial Date Of The New Book.
     * @param finalDate   The Final Date Of The New Book.
     * @throws EnvirifyPersistenceException When That Data Range Is Not Valid.
     */
    private void validateBookDates(List<String> books, Date initialDate, Date finalDate) throws EnvirifyPersistenceException {
        for (String bookId : books) {
            Book book;
            Optional<Book> optionalBook = repository.findById(bookId);
            if (optionalBook.isPresent()) {
                book = optionalBook.get();
                if (book.hasConflict(initialDate, finalDate)) {
                    throw new EnvirifyPersistenceException(EnvirifyPersistenceException.DATE_INTERVAL_ERROR);
                }
            }
        }
    }

}
