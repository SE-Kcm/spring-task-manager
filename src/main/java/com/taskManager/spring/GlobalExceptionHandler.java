package com.taskManager.spring;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<MessageResponseDTO> handleTaskNotFound(TaskNotFoundException ex){
       return ResponseEntity.status(404).body(new MessageResponseDTO(ex.getMessage()));
    }
}
