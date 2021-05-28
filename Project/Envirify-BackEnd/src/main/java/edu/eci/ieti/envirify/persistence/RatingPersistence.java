package edu.eci.ieti.envirify.persistence;

import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Place;
import edu.eci.ieti.envirify.model.Rating;

import java.util.List;

public interface RatingPersistence {

    void addRating(Rating rating, String id,String email) throws EnvirifyPersistenceException;


    /**
     * Gets ratings by place
     * @param place place that has the ratings
     * @return the ratings of that place
     * @throws EnvirifyPersistenceException if the place doesn't exist or the rating doesn't exist
     */
    List<Rating> getRatingsByPlace(Place place) throws EnvirifyPersistenceException;
}
