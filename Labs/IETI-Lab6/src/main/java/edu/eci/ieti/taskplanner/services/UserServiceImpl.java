package edu.eci.ieti.taskplanner.services;

import edu.eci.ieti.taskplanner.exceptions.TaskPlannerException;
import edu.eci.ieti.taskplanner.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final User[] usersMock = {
            new User(1, "daniel@gmail.com", "Daniel Walteros", "prueba"),
            new User(2, "felipe@gmail.com", "Felipe Trujillo", "password"),
            new User(3, "walteros@gmail.com", "Guillermo Walteros", "12345")};
    private final List<User> users = new CopyOnWriteArrayList<>(Arrays.asList(usersMock));

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User getById(long userId) throws TaskPlannerException {
        List<User> usersWithId = users.stream().filter(user -> user.getId() == userId).collect(Collectors.toList());
        if (usersWithId.size() != 1) {
            throw new TaskPlannerException("There is no user with the id " + userId, HttpStatus.NOT_FOUND);
        }
        return usersWithId.get(0);
    }

    @Override
    public User create(User user) {
        users.add(user);
        user.setId(users.size());
        return user;
    }

    @Override
    public User update(User user) throws TaskPlannerException {
        User oldUser = getById(user.getId());
        if (user.getEmail() != null) oldUser.setEmail(user.getEmail());
        if (user.getName() != null) oldUser.setName(user.getName());
        if (user.getPassword() != null) oldUser.setPassword(user.getPassword());
        return user;
    }

    @Override
    public void remove(long userId) throws TaskPlannerException {
        User user = getById(userId);
        users.remove(user);
    }
}
