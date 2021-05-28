package edu.eci.ieti.envirify.model;

import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Document Mapper Class For The Users On Envirify App.
 *
 * @author Error 418
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String gender;
    private String password;
    private List<String> books;
    private List<String> places;
    private List<String> chats;

    /**
     * Basic Constructor For User.
     */
    public User() {
    }

    /**
     * Constructor For User.
     *
     * @param userDTO The Create User Data Transfer Object.
     */
    public User(CreateUserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.name = userDTO.getName();
        this.phoneNumber = userDTO.getPhoneNumber();
        this.gender = userDTO.getGender();
        this.password = userDTO.getPassword();
        books = new ArrayList<>();
        places = new ArrayList<>();
        chats = new ArrayList<>();
    }

    /**
     * Returns the Id of the User.
     *
     * @return A string that represents the Id of the User.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets a New Id For The User.
     *
     * @param id The New Id For The User.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the Email of the User.
     *
     * @return A string that represents the Email of the User.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a New Email For The User.
     *
     * @param email The New Email For The User.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the Name of the User.
     *
     * @return A string that represents the Name of the User.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a New Name For The User.
     *
     * @param name The New Name For The User.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Phone Number of the User.
     *
     * @return A string that represents the Phone Number of the User.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets a New Phone Number For The User.
     *
     * @param phoneNumber The New Phone Number For The User.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the Gender of the User.
     *
     * @return A string that represents the Gender of the User.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets a New Gender For The User.
     *
     * @param gender The New Gender For The User.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the Password of the User.
     *
     * @return A string that represents the Password of the User.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a New Password For The User.
     *
     * @param password The New Password For The User.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the Books of the User.
     *
     * @return A collection of strings that represents the Books of the User.
     */
    public List<String> getBooks() {
        return books;
    }

    /**
     * Sets New Books For The User.
     *
     * @param books The New Books For The User.
     */
    public void setBooks(List<String> books) {
        this.books = books;
    }

    /**
     * Returns the Places of the User.
     *
     * @return A collection of strings that represents the Places of the User.
     */
    public List<String> getPlaces() {
        return places;
    }

    /**
     * Sets New Places For The User.
     *
     * @param places The New Places For The User.
     */
    public void setPlaces(List<String> places) {
        this.places = places;
    }

    /**
     * Returns the Chats of the User.
     *
     * @return A collection of strings that represents the Chats of the User.
     */
    public List<String> getChats() {
        return chats;
    }

    /**
     * Sets New Chats For The User.
     *
     * @param chats The New Chats For The User.
     */
    public void setChats(List<String> chats) {
        this.chats = chats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(gender, user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, phoneNumber, gender);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", books=" + books +
                ", places=" + places +
                ", chats=" + chats +
                '}';
    }

    /**
     * Add a place to a user
     *
     * @param idPlace the id of the place
     */
    public void addPlace(String idPlace) {
        places.add(idPlace);
    }

    /**
     * Adds A New Book To A User.
     *
     * @param bookId The Book Id.
     */
    public void addBook(String bookId) {
        books.add(bookId);
    }
}
