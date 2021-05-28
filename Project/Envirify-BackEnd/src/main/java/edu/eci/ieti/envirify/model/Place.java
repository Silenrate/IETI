package edu.eci.ieti.envirify.model;

import edu.eci.ieti.envirify.controllers.dtos.CreatePlaceDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Document Mapper Class For The Place On Envirify App.
 *
 * @author Error 418
 */
@Document(collection = "places")
public class Place {

    @Id
    private String id;
    private String name;
    private String department;
    private String city;
    private String direction;
    private String owner;
    private int capacity;
    private int habitations;
    private int bathrooms;
    private String description;
    private String urlImage;
    private List<String> guidebooks;
    private List<String> ratings;
    private List<String> bookings;


    /**
     * Basic constructor
     */
    public Place() {
    }

    /**
     * Constructor of the place
     *
     * @param placeDTO The Create Place Data Transfer Object.
     */
    public Place(CreatePlaceDTO placeDTO) {
        this.name = placeDTO.getName();
        this.department = placeDTO.getDepartment();
        this.city = placeDTO.getCity();
        this.direction = placeDTO.getDirection();
        this.capacity = placeDTO.getCapacity();
        this.habitations = placeDTO.getHabitations();
        this.bathrooms = placeDTO.getBathrooms();
        this.description = placeDTO.getDescription();
        this.urlImage = placeDTO.getUrlImage();
        this.owner = placeDTO.getOwner();
        guidebooks = new ArrayList<>();
        ratings = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    /**
     * get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set the name
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the department
     *
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * set the department
     *
     * @param department the department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * get the city
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * set the city
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * get the direction
     *
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * set the direction
     *
     * @param direction the direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * get the capacity
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * set the capacity
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * get the habitations
     *
     * @return the habitations
     */
    public int getHabitations() {
        return habitations;
    }


    /**
     * set the habitations
     *
     * @param habitations the habitations
     */
    public void setHabitations(int habitations) {
        this.habitations = habitations;
    }

    /**
     * get the bathrooms
     *
     * @return the bathrooms
     */
    public int getBathrooms() {
        return bathrooms;
    }

    /**
     * set the bathrooms
     *
     * @param bathrooms the bathrooms
     */
    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    /**
     * get the description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get the id
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * set the id
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get the guidebooks
     *
     * @return the guidebooks
     */
    public List<String> getGuidebooks() {
        return guidebooks;
    }

    /**
     * set the guidebooks
     *
     * @param guidebooks the guidebooks
     */
    public void setGuidebooks(List<String> guidebooks) {
        this.guidebooks = guidebooks;
    }

    /**
     * get the ratings
     *
     * @return the ratings
     */
    public List<String> getRatings() {
        return ratings;
    }


    /**
     * set the ratings
     *
     * @param ratings the ratings
     */
    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }

    /**
     * get the bookings
     *
     * @return the bookings
     */
    public List<String> getBookings() {
        return bookings;
    }

    /**
     * set the bookings
     *
     * @param bookings the bookings
     */
    public void setBookings(List<String> bookings) {
        this.bookings = bookings;
    }

    /**
     * Get the Image URL Of The Place
     *
     * @return The Image URL Of The Owner
     */
    public String getUrlImage() {
        return urlImage;
    }

    /**
     * Sets the Image URL Of The Place.
     *
     * @param urlImage The Image URL Of The Place.
     */
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    /**
     * Get the Owner Of The Place
     *
     * @return The Email Of The Owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner Of The Place
     *
     * @param owner The New Owner Of The Place
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(name, place.name) && Objects.equals(city, place.city) && Objects.equals(direction, place.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, direction);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", city='" + city + '\'' +
                ", direction='" + direction + '\'' +
                ", owner='" + owner + '\'' +
                ", capacity=" + capacity +
                ", habitations=" + habitations +
                ", bathrooms=" + bathrooms +
                ", description='" + description + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", guidebooks=" + guidebooks +
                ", ratings=" + ratings +
                ", bookings=" + bookings +
                '}';
    }

    /**
     * Adds A New Booking For The Place.
     *
     * @param bookId The New Booking Id.
     */
    public void addBook(String bookId) {
        bookings.add(bookId);
    }

    public void addRating(String ratingId){ratings.add(ratingId);}

    public void removeBooking(String bookingId) {
        bookings.remove(bookingId);
    }
}
