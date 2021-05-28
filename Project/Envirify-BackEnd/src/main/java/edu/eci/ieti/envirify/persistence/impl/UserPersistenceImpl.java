package edu.eci.ieti.envirify.persistence.impl;

import edu.eci.ieti.envirify.controllers.dtos.BookPlaceDTO;
import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import edu.eci.ieti.envirify.controllers.dtos.UserDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Book;
import edu.eci.ieti.envirify.model.Place;
import edu.eci.ieti.envirify.model.User;
import edu.eci.ieti.envirify.persistence.UserPersistence;
import edu.eci.ieti.envirify.persistence.repositories.BookRepository;
import edu.eci.ieti.envirify.persistence.repositories.PlaceRepository;
import edu.eci.ieti.envirify.persistence.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Class That Implements The User Persistence Methods For Envirify App.
 *
 * @author Error 418
 */
@Service
public class UserPersistenceImpl implements UserPersistence {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private BookRepository bookRepository;

    /**
     * Adds a New User On The DB.
     *
     * @param user The User That it is going to be added.
     * @throws EnvirifyPersistenceException When that user already exists.
     */
    @Override
    public void addUser(User user) throws EnvirifyPersistenceException {
        User oldUser = repository.findByEmail(user.getEmail());
        if (oldUser != null) {
            throw new EnvirifyPersistenceException("There is already a user with the " + user.getEmail() + " email address");
        }
        repository.save(user);
    }
    
    /**
     *  Update a user on the DB.
     *
     * @param user The User That it is going to be updated.
     * @throws EnvirifyPersistenceException When the email of the user to update already exists.
     */
    @Override
    public void updateUser(CreateUserDTO user, String email) throws EnvirifyPersistenceException {
    	User oldUser = getUserByEmail(email);
    	oldUser.setName(user.getName());
    	oldUser.setEmail(user.getEmail());
    	oldUser.setPhoneNumber(user.getPhoneNumber());
    	oldUser.setGender(user.getGender());
		repository.save(oldUser);
    }
	


    /**
     * Returns the Information Of A User With a Email From The DB.
     *
     * @param email The email to search the user.
     * @return The User Information.
     * @throws EnvirifyPersistenceException When that users do not exist.
     */
    @Override
    public User getUserByEmail(String email) throws EnvirifyPersistenceException {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new EnvirifyPersistenceException("There is no user with the email address "+email);
        }
        return user;
    }

	@Override
	public User getUserById(String id) throws EnvirifyPersistenceException {
		User user = null;
    	Optional<User> res = repository.findById(id);
		if (res.isPresent()) {
			user = res.get();
		}
		if (user == null) {
			throw new EnvirifyPersistenceException("There is no user");
		}
		return user;
	}

	private Book getBookById(String id) throws EnvirifyPersistenceException {
		Book book = null;
		Optional<Book> res = bookRepository.findById(id);
		if (res.isPresent()) {
			book = res.get();
		}
		if (book == null) {
	    	throw new EnvirifyPersistenceException("There is no booking");
		}
		return book;
	}
	
	/**
     * Returns the Bookings Of A User With a Email From The DB.
     *
     * @param email The email to search the bookings.
     * @return The User Bookings Information.
     * @throws EnvirifyPersistenceException When that user do not have bookings or that user do not exist.
     */
	 public List<BookPlaceDTO> getBookingsByEmail(String email) throws EnvirifyPersistenceException {
	        User user = repository.findByEmail(email);
	        if (user == null) {
	            throw new EnvirifyPersistenceException("There is no user with the email address "+email);
	        }
			List<String> lista = user.getBooks();
		    if (lista.isEmpty()) {
		    	throw new EnvirifyPersistenceException("There user with the email address "+email+" don't have bookings");
		    }
	        List<Book> bookings = new ArrayList<>() ;
		    for (String id:lista){
	            Book book = getBookById(id);
	            bookings.add(book);
	        }
		    List<Place> places = new ArrayList<>();
		    for (Book id:bookings){
		    	Place place = null;
		        Optional<Place> optionalPlace = placeRepository.findById(id.getPlaceId());
		        if (optionalPlace.isPresent()) {
		            place = optionalPlace.get();
		        }
		        if (place == null) {
		            throw new EnvirifyPersistenceException("There is no place with the id " + id);
		        }
	            places.add(place);
	        }

		 	List<BookPlaceDTO> booksDto= new ArrayList<>();
			 for (int i=0; i<bookings.size();i++){
				 Book book = bookings.get(i);
				 Place place = places.get(i);
				 BookPlaceDTO bookPlaceDTO= new BookPlaceDTO(book,place);
				 booksDto.add(bookPlaceDTO);
			 }

		    return booksDto;
		}

	public List<BookPlaceDTO> getBookingsToMe(String email) throws EnvirifyPersistenceException {
		User user = repository.findByEmail(email);
		if (user == null) {
			throw new EnvirifyPersistenceException("There is no user with the email address "+email);
		}

		List<Book> bookings = bookRepository.findAll();
		List<Place> places = new ArrayList<>();
		for (Book book:bookings){
			Place place = null;
			Optional<Place> optionalPlace = placeRepository.findById(book.getPlaceId());
			if (optionalPlace.isPresent()) {
				place = optionalPlace.get();
			}
			if (place == null) {
				throw new EnvirifyPersistenceException("There is no place with the id " + book.getPlaceId());
			}
			places.add(place);

		}

		List<BookPlaceDTO> booksDto= new ArrayList<>();
		for ( int i=0 ; i<places.size();i++){
			if (places.get(i).getOwner().equals(email)){
				Book book = bookings.get(i);
				BookPlaceDTO bookPlaceDTO= new BookPlaceDTO(book,places.get(i));
				booksDto.add(bookPlaceDTO);
			}
		}




		return booksDto;
	}
}
