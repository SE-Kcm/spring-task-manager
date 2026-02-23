package com.taskManager.spring;

public class AddTaskResponseDTO {
    private String name;
    private int id;
    private Task.Status status;
    private String message;

    
    public AddTaskResponseDTO(String name, int id, Task.Status status, String message) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.message = message;
    }

    public AddTaskResponseDTO(String message) {
        this.message = message;
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

    public String getMessage() {
        return message;
    }

}
