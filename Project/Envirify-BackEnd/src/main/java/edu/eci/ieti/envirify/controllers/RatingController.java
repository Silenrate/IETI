package edu.eci.ieti.envirify.controllers;

import edu.eci.ieti.envirify.controllers.dtos.RatingDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Rating;
import edu.eci.ieti.envirify.services.RatingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/ratings")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
public class RatingController {

    @Autowired
    private RatingServices services;


    @PostMapping()
    public ResponseEntity<Object> postRating(@RequestParam String placeId, @RequestBody RatingDTO ratingDTO,@RequestHeader("X-Email") String email) throws EnvirifyException {
        Rating rating = new Rating(ratingDTO);
        services.addRating(rating,placeId,email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * Get the ratings of a place of the Envirify App
     * @param placeId the id of the place that has the ratings
     * @return A Response Entity With The Request Status Code and the ratings of the place.
     * @throws EnvirifyPersistenceException When the ratings can not be got.
     */
    @GetMapping()
    public ResponseEntity<Object> getRatings(@RequestParam String placeId) throws EnvirifyPersistenceException {
        List<Rating> ratings = services.getRatingsByPlace(placeId);
        return new ResponseEntity<>(ratings,HttpStatus.ACCEPTED);
    }
}