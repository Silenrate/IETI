package com.eci.cosw.springbootsecureapi.controller;


import com.eci.cosw.springbootsecureapi.model.AssignTaskWrapper;
import com.eci.cosw.springbootsecureapi.model.Task;
import com.eci.cosw.springbootsecureapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/task")
    public List<Task> getTasksList() {
        return taskService.getTasksList();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/task")
    public Task createTask(@RequestBody Task task) {
        return taskService.create(task);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/task/assign")
    public Task assignTask(@RequestBody AssignTaskWrapper assignTaskWrapper) {
        return taskService.assignTaskToUser(assignTaskWrapper.getTaskId(), assignTaskWrapper.getUser());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/task/{userId}")
    public List<Task> getUserTasks(@PathVariable String userId) {
        return taskService.getTasksAssignedToUser(userId);
    }

}
