package edu.eci.ieti.envirify.services;

import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Place;

import java.util.List;

/**
 * Place Methods For Envirify App.
 *
 * @author Error 418
 */
public interface PlaceServices {

    /**
     * add a place of a user
     *
     * @param place the place
     * @param email the owner
     * @throws EnvirifyException When that place already exists or not exist the user.
     */
    void addPlace(Place place, String email) throws EnvirifyException;

    /**
     * Gets the places based on a city or department name.
     *
     * @param search The term to search
     * @return A List With The Places that are in the search term city or department.
     * @throws EnvirifyException When The Search Fails or does not have any result.
     */
    List<Place> getPlacesByCityOrDepartment(String search) throws EnvirifyException;

    /**
     * Gets A Place By His ID.
     *
     * @param id The Place Id.
     * @return The Place Class With That Id.
     * @throws EnvirifyException When The Place With That Id Does Not Exist.
     */
    Place getPlaceById(String id) throws EnvirifyException;

    List<Place> getPlaceByUser(String email) throws EnvirifyException;

	void deletePlaceById(String id, String email) throws EnvirifyException;

    /**
     * Updates a place
     * @param place to be updated
     * @return the updated place
     * @throws EnvirifyPersistenceException if the place to be updated doesn't exist
     */
    Place updatePlace(Place place) throws EnvirifyPersistenceException;



}
