package edu.eci.ieti.envirify.persistence;

import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Place;

import java.util.List;

/**
 * Place Persistence Methods For Envirify App.
 *
 * @author Error 418
 */
public interface PlacePersistence {

    /**
     * Add a new place to the db
     *
     * @param place the new place
     * @param email the email of the owner
     * @throws EnvirifyPersistenceException When that place already exists or not exist the user.
     */
    void addPlace(Place place, String email) throws EnvirifyPersistenceException;

    /**
     * Gets the places based on a city name.
     *
     * @param city The City Name to search.
     * @return A List With The Places that are in the search term city.
     */
    List<Place> getPlacesByCity(String city);

    /**
     * Gets the places based on a department name.
     *
     * @param department The Department Name to search.
     * @return A List With The Places that are in the search term department.
     */
    List<Place> getPlacesByDepartment(String department);

    /**
     * Gets A Place By His ID From DB.
     *
     * @param id The Place Id.
     * @return The Place Class With That Id.
     * @throws EnvirifyPersistenceException When The Place With That Id Does Not Exist.
     */
    Place getPlaceById(String id) throws EnvirifyPersistenceException;

    /**
     * Updates a place
     * @param place to be updated
     * @return the updated place
     * @throws EnvirifyPersistenceException if the place to be updated doesn't exist
     */
    Place updatePlace(Place place)throws EnvirifyPersistenceException;


    List<Place> getPlaceByUser(String email) throws EnvirifyPersistenceException;



	void deletePlaceById(String id, String email) throws EnvirifyPersistenceException;
}
