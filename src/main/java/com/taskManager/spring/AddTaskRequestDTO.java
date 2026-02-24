package com.taskManager.spring;

public class AddTaskRequestDTO {
    private String name;

    public AddTaskRequestDTO(String name) {
        this.name = name;
    }
    // Getters are required so that Jackson, the library used to convert Java objects to JSON, can access DTO fields and include them in the JSON response sent to the client
    public String getName() {
        return name;
    }

}
