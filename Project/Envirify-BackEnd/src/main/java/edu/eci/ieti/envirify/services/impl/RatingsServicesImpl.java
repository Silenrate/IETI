package edu.eci.ieti.envirify.services.impl;

import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Place;
import edu.eci.ieti.envirify.model.Rating;
import edu.eci.ieti.envirify.persistence.PlacePersistence;
import edu.eci.ieti.envirify.persistence.RatingPersistence;
import edu.eci.ieti.envirify.services.RatingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingsServicesImpl implements RatingServices {
    @Autowired
    private RatingPersistence persistence;
    @Autowired
    private PlacePersistence placePersistence;
    @Override
    public void addRating(Rating rating, String id,String email) throws EnvirifyException {
        try {
            persistence.addRating(rating, id,email);
        } catch (EnvirifyPersistenceException e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Rating> getRatingsByPlace(String idPlace) throws EnvirifyPersistenceException {
        Place place = placePersistence.getPlaceById(idPlace);
        return persistence.getRatingsByPlace(place);
    }
}
