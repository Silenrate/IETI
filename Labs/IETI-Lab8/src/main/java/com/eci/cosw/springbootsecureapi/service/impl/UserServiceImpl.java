package com.eci.cosw.springbootsecureapi.service.impl;

import com.eci.cosw.springbootsecureapi.model.User;
import com.eci.cosw.springbootsecureapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Santiago Carrillo
 * 8/21/17.
 */
@Service
public class UserServiceImpl implements UserService {

    private final List<User> users = new CopyOnWriteArrayList<>();

    public UserServiceImpl() {
        User user = new User("test@mail.com", "password", "Andres", "Perez");
        user.setId(1);
        users.add(user);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User getUser(Long id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public User createUser(User user) {
        long newId = users.size();
        user.setId(newId);
        users.add(user);
        return getUser(newId);
    }

    @Override
    public User findUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst().orElse(null);
    }

}
