package com.eci.cosw.springbootsecureapi.dtos;

import com.eci.cosw.springbootsecureapi.model.Task;

import java.util.Date;

public class TaskDTO {

    private String text;

    private String state;

    private Date dueDate;

    private UserDTO responsible;

    public TaskDTO() {
    }

    public TaskDTO(Task task) {
        this.text = task.getText();
        this.state = task.getState();
        this.dueDate = task.getDueDate();
        this.responsible = new UserDTO(task.getResponsible());
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

    public UserDTO getResponsible() {
        return responsible;
    }

    public void setResponsible(UserDTO responsible) {
        this.responsible = responsible;
    }
}
