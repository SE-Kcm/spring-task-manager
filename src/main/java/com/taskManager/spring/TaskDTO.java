package com.taskManager.spring;

import com.taskManager.spring.Task.Status;

public class TaskDTO {
    private String name;
    private int id;
    private Task.Status status;

    public TaskDTO(String name, int id, Status status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }

    // Getters are required so that Jackson, the library used to convert Java objects to JSON, can access DTO fields and include them in the JSON response sent to the client
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public Task.Status getStatus() {
        return status;
    }

    

    
}
