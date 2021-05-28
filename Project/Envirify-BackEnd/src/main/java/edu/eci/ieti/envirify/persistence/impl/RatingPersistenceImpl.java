package edu.eci.ieti.envirify.persistence.impl;

import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Place;
import edu.eci.ieti.envirify.model.Rating;
import edu.eci.ieti.envirify.persistence.RatingPersistence;
import edu.eci.ieti.envirify.persistence.repositories.PlaceRepository;
import edu.eci.ieti.envirify.persistence.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingPersistenceImpl implements RatingPersistence {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public void addRating(Rating rating,String id,String email) throws EnvirifyPersistenceException {
        rating.setOwner(email);
        Rating newRating= ratingRepository.save(rating);
        Place place = null;
        Optional<Place> optionalPlace = placeRepository.findById(id);
        if (optionalPlace.isPresent()) {
            place = optionalPlace.get();
        }
        if (place == null) {
            throw new EnvirifyPersistenceException("There is no place with the id " + id);
        }

        place.addRating(newRating.getId());
        placeRepository.save(place);
    }

    @Override
    public List<Rating> getRatingsByPlace(Place place) throws EnvirifyPersistenceException {
        List<String> ratingsIds = place.getRatings();
        List<Rating> ratings = new ArrayList<>();
        for(String id : ratingsIds){
            Optional<Rating> rating = ratingRepository.findById(id);
            if (!rating.isPresent()) {
                throw new EnvirifyPersistenceException("There is no rating with the id " + id);
            }
            ratings.add(rating.get());
        }
        return ratings;
    }
}
