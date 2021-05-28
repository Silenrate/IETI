package edu.eci.ieti.envirify.services.impl;

import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Place;
import edu.eci.ieti.envirify.persistence.PlacePersistence;
import edu.eci.ieti.envirify.services.PlaceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class That Implements The Place Methods For Envirify App.
 *
 * @author Error 418
 */
@Service
public class PlaceServicesImpl implements PlaceServices {

    @Autowired
    private PlacePersistence persistence;

    /**
     * add a place of a user
     *
     * @param place the place
     * @param email the owner
     * @throws EnvirifyException When that place already exists or not exist the user.
     */
    @Override
    public void addPlace(Place place, String email) throws EnvirifyException {
        try {
            persistence.addPlace(place, email);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.CONFLICT);
        }

    }

    /**
     * Gets the places based on a city or department name.
     *
     * @param search The term to search
     * @return A List With The Places that are in the search term city or department.
     * @throws EnvirifyException When The Search does not have any result.
     */
    @Override
    public List<Place> getPlacesByCityOrDepartment(String search) throws EnvirifyException {
        List<Place> searchByCityPlaces = persistence.getPlacesByCity(search);
        List<Place> searchByDepartmentPlaces = persistence.getPlacesByDepartment(search);
        List<Place> places = new ArrayList<>(searchByCityPlaces);
        searchByDepartmentPlaces.forEach(place -> {
            if (!places.contains(place)) {
                places.add(place);
            }
        });
        if (places.isEmpty()) {
            throw new EnvirifyException("There are no results for " + search, HttpStatus.NOT_FOUND);
        }
        return places;
    }

    /**
     * Gets A Place By His ID.
     *
     * @param id The Place Id.
     * @return The Place Class With That Id.
     * @throws EnvirifyException When The Place With That Id Does Not Exist.
     */
    @Override
    public Place getPlaceById(String id) throws EnvirifyException {
        Place place;
        try {
            place = persistence.getPlaceById(id);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
        return place;
    }

    @Override
    public List<Place> getPlaceByUser(String email) throws EnvirifyException {
        List<Place> places = new ArrayList<>();

        try {
            places=persistence.getPlaceByUser(email);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }

        if (places.isEmpty()) {
            throw new EnvirifyException("There are no places created by you", HttpStatus.NOT_FOUND);
        }
        return  places;
    }

	@Override
	public void deletePlaceById(String id, String email) throws EnvirifyException {
		try {
			persistence.deletePlaceById(id, email);
		} catch (EnvirifyPersistenceException e) {
			throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
		}
	}

    /**
     * Updates a place
     * @param place to be updated
     * @return the updated place
     * @throws EnvirifyPersistenceException if the place to be updated doesn't exist
     */
    @Override
    public Place updatePlace(Place place) throws EnvirifyPersistenceException {
        Place updatedPlace = persistence.updatePlace(place);
        return updatedPlace;
    }


}
