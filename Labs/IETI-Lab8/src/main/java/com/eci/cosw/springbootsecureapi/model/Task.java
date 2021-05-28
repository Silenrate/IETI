package com.eci.cosw.springbootsecureapi.model;

import com.eci.cosw.springbootsecureapi.dtos.TaskDTO;

import java.util.Date;

public class Task {

    private String text;
    private String state;
    private Date dueDate;
    private User responsible;

    public Task(String text, String state, Date dueDate, User responsible) {
        this.text = text;
        this.state = state;
        this.dueDate = dueDate;
        this.responsible = responsible;
    }

    public Task(TaskDTO taskDTO) {
        this.text = taskDTO.getText();
        this.state = taskDTO.getState();
        this.dueDate = taskDTO.getDueDate();
        this.responsible = new User(taskDTO.getResponsible());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }
}
