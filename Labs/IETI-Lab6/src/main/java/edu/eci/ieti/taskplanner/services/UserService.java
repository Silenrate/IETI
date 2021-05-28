package edu.eci.ieti.taskplanner.services;

import edu.eci.ieti.taskplanner.exceptions.TaskPlannerException;
import edu.eci.ieti.taskplanner.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(long userId) throws TaskPlannerException;

    User create(User user);

    User update(User user) throws TaskPlannerException;

    void remove(long userId) throws TaskPlannerException;
}
