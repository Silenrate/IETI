package edu.eci.ieti.envirify.controllers;

import edu.eci.ieti.envirify.controllers.dtos.CreatePlaceDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Place;
import edu.eci.ieti.envirify.services.PlaceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST Controller for User Functions Of The Envirify App.
 *
 * @author Error 418
 */

@RestController
@RequestMapping(value = "api/v1/places")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
public class PlaceController {

    @Autowired
    private PlaceServices services;

    /**
     * Gets A Place By His ID.
     *
     * @param id The Place Id.
     * @return A Response Entity With The Place Or With The Error Message.
     * @throws EnvirifyException When The Place With That Id Does Not Exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPlaceById(@PathVariable String id) throws EnvirifyException {
        Place place = services.getPlaceById(id);
        CreatePlaceDTO placeDTO = new CreatePlaceDTO(place);
        return new ResponseEntity<>(placeDTO, HttpStatus.OK);
    }
    
    /**
     * Delete a place by his ID.
     *
     * @param id The place id.
     * @return A response entity with the delete place or with the error message.
     * @throws EnvirifyException When the place with that id does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePlaceById(@PathVariable String id, @RequestHeader ("X-Email") String email) throws EnvirifyException {
    	services.deletePlaceById(id, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets the places based on a city or department name.
     *
     * @param search The term to search, if it is not provided we calculate based on the Colombian Most Populates Place Bogota.
     * @return A Response Entity With The Places Or With The Error Message.
     * @throws EnvirifyException When The Consult Fails.
     */
    @GetMapping()
    public ResponseEntity<Object> getPlacesByCityOrDepartment(@RequestParam(defaultValue = "Bogota") String search) throws EnvirifyException {
        List<Place> places = services.getPlacesByCityOrDepartment(search);
        List<CreatePlaceDTO> placeDTOS = new ArrayList<>();
        places.forEach(place -> placeDTOS.add(new CreatePlaceDTO(place)));
        return new ResponseEntity<>(placeDTOS, HttpStatus.OK);
    }

    /**
     * Adds a new place of a user to the Envirify App
     *
     * @param placeDTO new place
     * @param email    email of user
     * @return A Response Entity With The Request Status Code.
     * @throws EnvirifyException When the place cannot be created.
     */
    @PostMapping()
    public ResponseEntity<Object> addNewPlace(@RequestBody CreatePlaceDTO placeDTO, @RequestHeader("X-Email") String email) throws EnvirifyException {
        Place newPlace = new Place(placeDTO);
        services.addPlace(newPlace, email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Updates a new place of the Envirify App
     * @param placeDTO place to be updated
     * @param id id of the place to be updated
     * @param email email of the user
     * @return A Response Entity With The Request Status Code.
     * @throws EnvirifyPersistenceException When the place cannot be updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePlaceById(@RequestBody CreatePlaceDTO placeDTO, @PathVariable String id , @RequestHeader("X-Email") String email) throws  EnvirifyPersistenceException {
        Place newPlace = new Place(placeDTO);
        newPlace.setId(id);
        Place updatedPlace = services.updatePlace(newPlace);
        return new ResponseEntity<>(updatedPlace,HttpStatus.CREATED);
    }

    @GetMapping("/myplaces")
    public ResponseEntity<Object> getPlaceByUser(@RequestHeader("X-Email") String email) throws EnvirifyException {
        List<Place> places = services.getPlaceByUser(email);
        return new ResponseEntity<>(places, HttpStatus.OK);
    }



}
