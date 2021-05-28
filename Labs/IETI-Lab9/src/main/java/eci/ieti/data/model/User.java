package eci.ieti.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String name;

    private String email;

    private int todosAmount;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        todosAmount = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTodosAmount() {
        return todosAmount;
    }

    public void setTodosAmount(int todosAmount) {
        this.todosAmount = todosAmount;
    }

    public void increaseTodoAmount() {
        todosAmount++;
    }

    @Override
    public String toString() {
        return "User[" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", todosAmount=" + todosAmount +
                ']';
    }
}
