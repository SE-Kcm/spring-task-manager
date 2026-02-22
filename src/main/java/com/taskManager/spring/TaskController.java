package com.taskManager.spring;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController{

    TaskService taskService;
    
    public TaskController(TaskService taskService) { 
        this.taskService = taskService;
    }
    @RequestMapping(value="/tasks", method=RequestMethod.POST)
    public void addTask(@RequestBody Map<String, String> body){
        taskService.addTask(body.get("name"));
    }

    @RequestMapping(value="/tasks", method=RequestMethod.GET)
    public List<Task> listAllTasks(){
        return taskService.getAllTasks(); 
    }

    @RequestMapping(value="/tasks/{id}/done", method=RequestMethod.PUT)
    public void markTask(@PathVariable int id){
        taskService.markTask(id);
    }

    @RequestMapping(value="/tasks/{id}", method=RequestMethod.DELETE)
    public void deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
    }

}
