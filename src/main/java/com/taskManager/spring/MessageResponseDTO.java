package com.taskManager.spring;

public class MessageResponseDTO {
    private String message;

    public MessageResponseDTO(String message) {
        this.message = message;
    }
    // Getters are required so that Jackson, the library used to convert Java objects to JSON, can access DTO fields and include them in the JSON response sent to the client
    public String getMessage() {
        return message;
    }

}
