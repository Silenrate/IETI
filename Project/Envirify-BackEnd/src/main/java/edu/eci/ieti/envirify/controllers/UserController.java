package edu.eci.ieti.envirify.controllers;

import edu.eci.ieti.envirify.controllers.dtos.BookPlaceDTO;
import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import edu.eci.ieti.envirify.controllers.dtos.LoginDTO;
import edu.eci.ieti.envirify.controllers.dtos.UserDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.model.User;
import edu.eci.ieti.envirify.security.jwt.JwtResponse;
import edu.eci.ieti.envirify.security.jwt.JwtUtils;
import edu.eci.ieti.envirify.security.userdetails.UserDetailsImpl;
import edu.eci.ieti.envirify.services.UserServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for User Functions Of The Envirify App.
 *
 * @author Error 418
 */
@RestController
@RequestMapping(value = "api/v1/users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class UserController {

    @Autowired
    private UserServices services;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Returns the Information Of A User With a Email.
     *
     * @param email The email to search the user.
     * @return The Response Entity With The User Information Or The Error Message.
     * @throws EnvirifyException When the user can not be Searched.
     */
    @GetMapping("/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) throws EnvirifyException {
        return new ResponseEntity<>(new UserDTO(services.getUserByEmail(email)), HttpStatus.ACCEPTED);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Object> getUserByID(@PathVariable String id) throws EnvirifyException {
        return new ResponseEntity<>(new UserDTO(services.getUserById(id)), HttpStatus.ACCEPTED);
    }
    
    /**
     * Returns the bookings of a user with a email.
     *
     * @param email The email to search the user.
     * @return The Response Entity With The User Information Or The Error Message.
     * @throws EnvirifyException When the user can not be Searched or not have bookings.
     */
    @GetMapping("/{email}/bookings")
    public ResponseEntity<Object> getBookingsByEmail(@PathVariable String email) throws EnvirifyException {
    	List<BookPlaceDTO> lista1= services.getBookingsByEmail(email);
    	return new ResponseEntity<>(lista1, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{email}/books")
    public ResponseEntity<Object> getBookingsToMe(@PathVariable String email) throws EnvirifyException {
        List<BookPlaceDTO> books= services.getBookingsToMe(email);
        return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
    }
    
    /**
     * Adds A New User On The Envirify App.
     *
     * @param userDTO Object that represents the body of the HTTP Request Of The User.
     * @return A Response Entity With The Request Status Code.
     * @throws EnvirifyException When the user cannot be created.
     */
    @PostMapping()
    public ResponseEntity<Object> addNewUser(@RequestBody CreateUserDTO userDTO) throws EnvirifyException {
        User newUser = new User(userDTO);
        newUser.setPassword(encoder.encode(userDTO.getPassword()));
        services.addUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update a user on the envirify app.
     *
     * @param userDTO The User That it is going to be updated.
     * @return A Response Entity With The Request Status Code.
     * @throws EnvirifyException When the user cannot be updated.
     */
    @PutMapping("/{email}")
    public ResponseEntity<Object> updateUser(@RequestBody CreateUserDTO userDTO, @PathVariable String email) throws EnvirifyException {
        services.updateUser(userDTO, email);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Log in to the envirify app
     *
     * @param loginDTO Object that represents a login request to the app
     * @return A Response Entity With The with the JWT response.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        );
        return new ResponseEntity<>(jwtResponse, HttpStatus.ACCEPTED);
    }


}
