package edu.eci.ieti.envirify.persistence.impl;

import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Book;
import edu.eci.ieti.envirify.model.Place;
import edu.eci.ieti.envirify.model.User;
import edu.eci.ieti.envirify.persistence.PlacePersistence;
import edu.eci.ieti.envirify.persistence.repositories.BookRepository;
import edu.eci.ieti.envirify.persistence.repositories.PlaceRepository;
import edu.eci.ieti.envirify.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class That Implements The Place Persistence Methods For Envirify App.
 *
 * @author Error 418
 */
@Service
public class PlacePersistenceImpl implements PlacePersistence {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Add a place of a user
     *
     * @param place the new place
     * @param email the email of the owner
     * @throws EnvirifyPersistenceException When the user does not exist or tha place already exists.
     */
    @Override
    public void addPlace(Place place, String email) throws EnvirifyPersistenceException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EnvirifyPersistenceException("There is no user with the email address " + email);
        }
        List<Place> oldPlace = placeRepository.findByDirection(place.getDirection());
        if (!oldPlace.isEmpty()) {
            Place city = oldPlace.stream().filter(c -> place.getCity().equals(c.getCity())).findFirst().orElse(null);
            if (city != null) {
                throw new EnvirifyPersistenceException("There is already a place with the " + place.getDirection() + "-" + place.getCity() + "-address");
            }
        }
        place.setOwner(email);
        Place newPlace = placeRepository.save(place);
        user.addPlace(newPlace.getId());
        userRepository.save(user);
    }

    /**
     * Gets the places based on a city name.
     *
     * @param city The City Name to search.
     * @return A List With The Places that are in the search term city.
     */
    @Override
    public List<Place> getPlacesByCity(String city) {
        return placeRepository.findByCity(city);
    }

    /**
     * Gets the places based on a department name.
     *
     * @param department The Department Name to search.
     * @return A List With The Places that are in the search term department.
     */
    @Override
    public List<Place> getPlacesByDepartment(String department) {
        return placeRepository.findByDepartment(department);
    }

    /**
     * Gets A Place By His ID From DB.
     *
     * @param id The Place Id.
     * @return The Place Class With That Id.
     * @throws EnvirifyPersistenceException When The Place With That Id Does Not Exist.
     */
    @Override
    public Place getPlaceById(String id) throws EnvirifyPersistenceException {
        Place place = null;
        Optional<Place> optionalPlace = placeRepository.findById(id);
        if (optionalPlace.isPresent()) {
            place = optionalPlace.get();
        }
        if (place == null) {
            throw new EnvirifyPersistenceException("There is no place with the id " + id);
        }
        return place;
    }

    /**
     * Updates a place
     * @param place to be updated
     * @return the updated place
     * @throws EnvirifyPersistenceException if the place to be updated doesn't exist
     */
    @Override
    public Place updatePlace(Place place) throws EnvirifyPersistenceException {
        Place oldPlace = getPlaceById(place.getId());
        if (place.getId() == null) { place.setId(oldPlace.getId()); }
        if(place.getName() == null ){ place.setName(oldPlace.getName()); }
        if(place.getDepartment() == null) { place.setDepartment(oldPlace.getDepartment()) ;};
        if(place.getCity() == null) { place.setCity(oldPlace.getCity()) ; }
        if(place.getDirection() == null ){ place.setDirection(oldPlace.getDirection()); }
        if(place.getOwner() == null ){place.setOwner(oldPlace.getOwner());}
        if(place.getCapacity() == 0){place.setCapacity(oldPlace.getCapacity());}
        if(place.getHabitations() == 0 ){place.setHabitations(oldPlace.getHabitations());}
        if(place.getBathrooms() == 0){place.setBathrooms(oldPlace.getBathrooms());}
        if(place.getDescription() == null){place.setDescription(oldPlace.getDescription());}
        if(place.getUrlImage() == null){place.setUrlImage(place.getUrlImage());}
        if(place.getGuidebooks() == null){place.setGuidebooks(oldPlace.getGuidebooks());}
        if(place.getRatings() == null){place.setRatings(oldPlace.getRatings());}
        if(place.getBookings() == null){place.setBookings(oldPlace.getBookings());}
        placeRepository.deleteById(oldPlace.getId());
        placeRepository.save(place);
        return place;
    }


    @Override
    public List<Place> getPlaceByUser(String email) throws EnvirifyPersistenceException {
        User user = userRepository.findByEmail(email);
        List<String> ids = user.getPlaces();
        List<Place> places = new ArrayList<>() ;
        for (String id:ids){
            Place place = getPlaceById(id);
            places.add(place);
        }
        return places;
    }


    @Override
	public void deletePlaceById(String id, String email) throws EnvirifyPersistenceException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
            throw new EnvirifyPersistenceException("There is no user with the email address " + email);
		}
			List<String> places = user.getPlaces(); 
			if (places.isEmpty()) {
	            throw new EnvirifyPersistenceException("There is no place with the id " + id);
			}
			places.remove(id);
			user.setPlaces(places);
			userRepository.save(user);
			placeRepository.deleteById(id, email);
			List<Book> bookings = bookRepository.findAll();
			List<Book> list = bookings.stream().filter(e -> e.getPlaceId().equals(id)).collect(Collectors.toList());
			if(!list.isEmpty()) {
				for(Book book: list) {
					bookRepository.deleteById(book.getId());
					Optional<User> us = userRepository.findById(book.getUserId());
					User newUser;
					if(!us.isPresent()) {
			            throw new EnvirifyPersistenceException("There is no user with the id: "+book.getUserId());
					}
					newUser = us.get();
					List<String> listt= newUser.getBooks();
					listt.remove(book.getId());
					newUser.setBooks(listt);
					userRepository.save(newUser);
				}
			}
		}
}
