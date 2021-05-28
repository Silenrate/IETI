package edu.eci.ieti.envirify.services;

import java.util.List;

import edu.eci.ieti.envirify.controllers.dtos.BookPlaceDTO;
import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import edu.eci.ieti.envirify.controllers.dtos.UserDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.model.User;

/**
 * User Methods For Envirify App.
 *
 * @author Error 418
 */
public interface UserServices {

    /**
     * Adds a New User On The App.
     *
     * @param user The User That it is going to be added.
     * @throws EnvirifyException When that user cannot be added.
     */
    void addUser(User user) throws EnvirifyException;
    
    /**
     * Update a user on the app.
     *
     * @param user The User That it is going to be updated.
     * @throws EnvirifyException When that user cannot be updated.
     */
    void updateUser(CreateUserDTO user, String email) throws EnvirifyException;

    /**
     * Returns the Information Of A User With a Email.
     *
     * @param email The email to search the user.
     * @return The User Information.
     * @throws EnvirifyException When that users do not exist.
     */
    User getUserByEmail(String email) throws EnvirifyException;

    User getUserById(String id) throws EnvirifyException;

    /**
     * Returns the bookings Of A User With a Email.
     *
     * @param email The email to search the bookings.
     * @return The User Bookings Information.
     * @throws EnvirifyException When that user do not have bookings or that user do not exist.
     */
	List<BookPlaceDTO> getBookingsByEmail(String email) throws EnvirifyException;

    List<BookPlaceDTO> getBookingsToMe(String email) throws EnvirifyException;

}
