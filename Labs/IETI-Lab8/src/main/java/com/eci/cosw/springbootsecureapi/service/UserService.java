package com.eci.cosw.springbootsecureapi.service;

import com.eci.cosw.springbootsecureapi.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author Santiago Carrillo
 * 8/21/17.
 */
public interface UserService {

    List<User> getUsers();

    User getUser(Long id);

    User createUser(User user);

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);
}