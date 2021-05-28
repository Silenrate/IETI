package com.eci.cosw.springbootsecureapi.controller;

import com.eci.cosw.springbootsecureapi.dtos.LoginDTO;
import com.eci.cosw.springbootsecureapi.model.User;
import com.eci.cosw.springbootsecureapi.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Santiago Carrillo
 * 8/21/17.
 */
@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO login) {
        String jwtToken;
        if (login.getUsername() == null || login.getPassword() == null) {
            return new ResponseEntity<>("Please fill in username and password", HttpStatus.BAD_REQUEST);
        }
        String username = login.getUsername();
        String password = login.getPassword();
        User user = userService.findUserByEmail(username);
        if (user == null) {
            return new ResponseEntity<>("User username not found.", HttpStatus.CONFLICT);
        }
        String pwd = user.getPassword();
        if (!password.equals(pwd)) {
            return new ResponseEntity<>("Invalid login. Please check your name and password.", HttpStatus.CONFLICT);
        }
        jwtToken = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date()).signWith(
                SignatureAlgorithm.HS256, "secretkey").compact();
        Token token = new Token(jwtToken);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    public class Token {
        String accessToken;

        public Token(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
