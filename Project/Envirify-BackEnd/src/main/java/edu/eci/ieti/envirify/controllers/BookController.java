package edu.eci.ieti.envirify.controllers;

import edu.eci.ieti.envirify.controllers.dtos.BookDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.model.Book;
import edu.eci.ieti.envirify.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for Books.
 */
@RestController
@RequestMapping(value = "api/v1/books")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE})
public class BookController {

    @Autowired
    private BookServices services;

    /**
     * Adds a New Booking On The App.
     *
     * @param bookDTO  The Book Information.
     * @param email The Email Of The User.
     * @return A Response Entity With The Response Status.
     * @throws EnvirifyException When Something Fails.
     */
    @PostMapping()
    public ResponseEntity<Object> addNewBooking(@RequestBody BookDTO bookDTO, @RequestHeader("X-Email") String email) throws EnvirifyException {
        Book book = new Book(bookDTO);
        services.addNewBooking(book, email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Deletes A Booking With His Id.
     *
     * @param bookingId The Booking Id.
     * @param email     The User Email.
     * @return A Response Entity With The Response Status.
     * @throws EnvirifyException When Something Fails.
     */
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Object> deleteBooking(@PathVariable String bookingId, @RequestHeader("X-Email") String email) throws EnvirifyException {
        services.deleteBooking(bookingId, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
