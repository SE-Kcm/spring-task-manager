package com.taskManager.spring;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController{

    TaskService taskService;
    
    public TaskController(TaskService taskService) { 
        this.taskService = taskService;
    }
    @PostMapping(value="/tasks")
    public ResponseEntity<String> addTask(@RequestBody Map<String, String> body){
        String taskName = body.get("name");
        if(taskName != null && !taskName.isEmpty()){
            taskService.addTask(taskName);
            return ResponseEntity.ok("Task added successfully!");
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value="/tasks")
    public ResponseEntity<List<Task>> listAllTasks(){
        List<Task> resList =  taskService.getAllTasks(); 
        if(resList.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(resList);
        }
        
    }

    @PutMapping(value="/tasks/{id}/done")
    public ResponseEntity<String> markTask(@PathVariable int id){
        boolean resVal = taskService.markTask(id);
        if(resVal){
            return ResponseEntity.ok("Task marked as DONE!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value="/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id){
        boolean resVal = taskService.deleteTask(id);
        if(resVal){
            return ResponseEntity.ok("Task deleted successfully!");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
