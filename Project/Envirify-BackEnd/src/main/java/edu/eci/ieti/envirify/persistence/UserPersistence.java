package edu.eci.ieti.envirify.persistence;

import java.util.List;

import edu.eci.ieti.envirify.controllers.dtos.BookPlaceDTO;
import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import edu.eci.ieti.envirify.controllers.dtos.UserDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.User;

/**
 * User Persistence Methods For Envirify App.
 *
 * @author Error 418
 */
public interface UserPersistence {
    /**
     * Adds a New User On The DB.
     *
     * @param user The User That it is going to be added.
     * @throws EnvirifyPersistenceException When that user already exists.
     */
    void addUser(User user) throws EnvirifyPersistenceException;
    
    /**
     * Update a user on the DB.
     *
     * @param user The User That it is going to be updated.
     * @throws EnvirifyPersistenceException When the email of the user to update already exists.
     */
    void updateUser(CreateUserDTO user, String email) throws EnvirifyPersistenceException;

    /**
     * Returns the Information Of A User With a Email From The DB.
     *
     * @param email The email to search the user.
     * @return The User Information.
     * @throws EnvirifyPersistenceException When that users do not exist.
     */
    User getUserByEmail(String email) throws EnvirifyPersistenceException;

    User getUserById(String id) throws EnvirifyPersistenceException;

    /**
     * Returns the Bookings Of A User With a Email From The DB.
     *
     * @param email The email to search the bookings.
     * @return The User Bookings Information.
     * @throws EnvirifyPersistenceException When that user do not have booking or that user do not exist.
     */
	List<BookPlaceDTO> getBookingsByEmail(String email) throws EnvirifyPersistenceException;

    List<BookPlaceDTO> getBookingsToMe(String email) throws EnvirifyPersistenceException;


}
