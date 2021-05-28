package edu.eci.ieti.envirify.services;

import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Rating;

import java.util.List;

public interface RatingServices {
    void addRating(Rating rating, String id,String email) throws EnvirifyException;

    /**
     * Gets ratings by place
     * @param idPlace id of the place that has the ratings
     * @return the ratings of that place
     * @throws EnvirifyPersistenceException if the place doesn't exist or the rating doesn't exist
     */
    List<Rating> getRatingsByPlace(String idPlace) throws EnvirifyPersistenceException;
}
