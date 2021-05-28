package edu.eci.ieti.envirify.services.impl;

import edu.eci.ieti.envirify.controllers.dtos.BookPlaceDTO;
import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import edu.eci.ieti.envirify.controllers.dtos.UserDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.User;
import edu.eci.ieti.envirify.persistence.UserPersistence;
import edu.eci.ieti.envirify.services.UserServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


/**
 * Class That Implements The User Methods For Envirify App.
 *
 * @author Error 418
 */
@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserPersistence persistence;

    /**
     * Adds a New User On The App.
     *
     * @param user The User That it is going to be added.
     * @throws EnvirifyException When that user cannot be added.
     */
    @Override
    public void addUser(User user) throws EnvirifyException {
        try {
            persistence.addUser(user);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.CONFLICT);
        }
    }
    
    /**
     * Update a user on the app.
     *
     * @param user The User That it is going to be updated.
     * @throws EnvirifyException When that user cannot be updated.
     */
    @Override
    public void updateUser(CreateUserDTO user, String email) throws EnvirifyException {
        try {
            persistence.updateUser(user, email);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.CONFLICT);
        }
    }

    /**
     * Returns the Information Of A User With a Email.
     *
     * @param email The email to search the user.
     * @return The User Information.
     * @throws EnvirifyException When that users do not exist.
     */
    @Override
    public User getUserByEmail(String email) throws EnvirifyException {
        User user;
        try {
            user = persistence.getUserByEmail(email);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
        return user;
    }

    @Override
    public User getUserById(String id) throws EnvirifyException {
        User user;
        try {
            user = persistence.getUserById(id);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
        return user;
    }

    /**
     * Returns the Bookings Of A User With a Email.
     *
     * @param email The email to search the bookings.
     * @return The User Bookings Information.
     * @throws EnvirifyException When that user do not have bookings or that user do not exist.
     */
	@Override
	public List<BookPlaceDTO> getBookingsByEmail(String email) throws EnvirifyException {
		List <BookPlaceDTO> lista;
        try {
            lista = persistence.getBookingsByEmail(email);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
        return lista;	}

    @Override
    public List<BookPlaceDTO> getBookingsToMe(String email) throws EnvirifyException {
        List <BookPlaceDTO> books;
        try {
            books = persistence.getBookingsToMe(email);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
        return books;	}


}
