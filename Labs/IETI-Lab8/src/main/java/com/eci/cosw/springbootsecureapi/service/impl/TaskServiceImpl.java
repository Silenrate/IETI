package com.eci.cosw.springbootsecureapi.service.impl;

import com.eci.cosw.springbootsecureapi.model.Task;
import com.eci.cosw.springbootsecureapi.model.User;
import com.eci.cosw.springbootsecureapi.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TaskServiceImpl implements TaskService {

    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    public TaskServiceImpl() {
        User user = new User("test@mail.com", "password", "Andres", "Perez");
        user.setId(1);
        user.setUsername("usuario");
        tasks.add(new Task("Do The Front", "ready", new Date(), user));
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }
}
