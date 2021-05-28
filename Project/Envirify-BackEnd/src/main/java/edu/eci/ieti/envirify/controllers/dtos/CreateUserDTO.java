package edu.eci.ieti.envirify.controllers.dtos;

import java.io.Serializable;

/**
 * Data Transfer Object For Create User Request.
 *
 * @author Error 418
 */
public class CreateUserDTO implements Serializable {

    private String email;
    private String name;
    private String phoneNumber;
    private String gender;
    private String password;

    /**
     * Basic Constructor For CreateUserDTO.
     */
    public CreateUserDTO() {
        //Left Empty On Purpose For The Controllers Conversion.
    }

    /**
     * Constructor For CreateUserDTO.
     *
     * @param email       The Email Of The User.
     * @param name        The Name Of The User.
     * @param phoneNumber The Phone Number Of The User.
     * @param gender      The Gender Of The User.
     * @param password    The Password Of The User.
     */
    public CreateUserDTO(String email, String name, String phoneNumber, String gender, String password) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.password = password;
    }

    /**
     * Returns the email of the User.
     *
     * @return A string that represents the email of the User.
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
     * Returns the name of the User.
     *
     * @return A string that represents the name of the User.
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
     * Returns the phone number of the User.
     *
     * @return A string that represents the phone number of the User.
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
     * Returns the gender of the User.
     *
     * @return A string that represents the gender of the User.
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
     * Returns the password of the User.
     *
     * @return A string that represents the password of the User.
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


}
