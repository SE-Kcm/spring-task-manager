package com.taskManager.spring;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private int id; 
    private String name; 
    enum Status {TODO, DONE}
    @Enumerated(EnumType.STRING) 
    private Status status;

    public Task() {
    }

    public Task(String name, Task.Status status) { 
        this.name = name;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }


}
