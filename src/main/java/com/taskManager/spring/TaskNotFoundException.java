package com.taskManager.spring;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(int id){
        super("Task with ID " + id + " could not be found!");
    }

}
