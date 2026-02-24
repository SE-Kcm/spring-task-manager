package com.taskManager.spring;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) { 
        this.taskRepository = taskRepository;
    }

    public Task addTask(String taskName){
        Task task = new Task(taskName, Task.Status.TODO);
        return taskRepository.save(task);
        
    }

    public void deleteTask(int id){
        Task t = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)); 
        taskRepository.delete(t);      
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    
    @Transactional 
    public void markTask(int id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(Task.Status.DONE);
    }
}
