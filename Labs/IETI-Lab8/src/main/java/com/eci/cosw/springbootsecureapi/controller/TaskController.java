package com.eci.cosw.springbootsecureapi.controller;

import com.eci.cosw.springbootsecureapi.dtos.TaskDTO;
import com.eci.cosw.springbootsecureapi.model.Task;
import com.eci.cosw.springbootsecureapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<Object> getTasks() {
        List<TaskDTO> taskDTOS = new CopyOnWriteArrayList<>();
        List<Task> tasks = service.getTasks();
        tasks.forEach(task -> taskDTOS.add(new TaskDTO(task)));
        return new ResponseEntity<>(taskDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addTask(@RequestBody TaskDTO taskDTO) {
        service.addTask(new Task(taskDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
