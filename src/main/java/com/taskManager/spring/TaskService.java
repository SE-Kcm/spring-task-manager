package com.taskManager.spring;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) { 
        this.taskRepository = taskRepository;
    }

    public void addTask(String taskName){
        Task task = new Task(taskName, Task.Status.TODO);
        taskRepository.save(task);
    }

    public boolean deleteTask(int id){
        Optional<Task> t = taskRepository.findById(id); 
        if(t.isPresent()){
            taskRepository.delete(t.get());
            return true;
        }else{
            return false;
        }
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    @Transactional 
    public boolean markTask(int id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            task.get().setStatus(Task.Status.DONE);
            return true;
        }else{
            return false;
        }
    }
}
