package edu.eci.ieti.envirify.security.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import edu.eci.ieti.envirify.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class that get the details of a user
 * taken from : https://bezkoder.com/spring-boot-jwt-auth-mongodb/#Implement_UserDetails_038_UserDetailsService
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    /**
     * Constructor of the class UserDetailsImpl
     * @param id id of the user
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     */
    public UserDetailsImpl(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * It is in charge of building the user's details
     * @param user user to be builded
     * @return the user details of the user
     */
    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword());
    }

    /**
     * Returns the id of the user
     * @return the id of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the email of the user
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the authorities that has the user
     * @return the authorities that has the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    /**
     * Returns the password of the user
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username of the user
     * @return the username of the user
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Check if the account is not expired
     * @return true if the account is not expired , false if it is
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the accountt is not locked
     * @return true if the account is not locked , false if it is
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the credentials are not expired
     * @return true if the credentials are not expired , false if they are
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the account is enabled
     * @return true if the account is enabled , false if not
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o  Object to be compared
     * @return true if the object is "equal to" this one , false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}