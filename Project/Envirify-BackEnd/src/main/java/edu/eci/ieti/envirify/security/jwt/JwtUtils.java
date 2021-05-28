package edu.eci.ieti.envirify.security.jwt;

import java.util.Date;

import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

/**
 * Class that is responsible for generating and validating a JWT token
 * Taken from : https://bezkoder.com/spring-boot-jwt-auth-mongodb/#Implement_UserDetails_038_UserDetailsService
 */
@Component
public class JwtUtils {

    @Value("${envirify.app.jwtSecret}")
    private String jwtSecret;

    @Value("${envirify.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Method that generates a jwt authentication token
     * @param authentication Object containing the details of the user on which the token will be generated
     * @return the jwt authentication token
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Gets the email of the user from a jwt token
     * @param token the jwt authentication token
     * @return the email of the user
     */
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validate that the structure corresponds to a jwt authentication token
     * @param authToken the jwt authentication token to be validated
     * @return true if it is a jwt token, false if not
     */
    public boolean validateJwtToken(String authToken) throws EnvirifyException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            throw new EnvirifyException(e.getMessage(), e, HttpStatus.BAD_REQUEST);
        }
    }
}