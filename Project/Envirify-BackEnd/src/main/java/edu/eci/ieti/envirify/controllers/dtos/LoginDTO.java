package edu.eci.ieti.envirify.controllers.dtos;

/**
 * Data Transfer Object For Login Request
 *
 * @author Error 418
 */
public class LoginDTO {

    private String email;
    private String password;

    /**
     * Constructor of LoginDTO.
     * @param email email of the login request
     * @param password password of the login request
     */
    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Basic Constructor For LoginDTO.
     */
    public LoginDTO() {
    }

    /**
     * Returns the email of the request
     * @return the email of the request
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the request
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the request
     * @return the password of the request
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the request
     * @param password the password of the request
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
