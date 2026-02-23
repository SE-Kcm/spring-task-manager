package com.taskManager.spring;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> addTask(@RequestBody Map<String, String> body){
        String taskName = body.get("name");
        if(!taskName.isEmpty() && taskName != null){
            taskService.addTask(taskName);
            return ResponseEntity.ok().build(); //need build because there is no response body (build: Build the response entity with no body.)
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value="/tasks", method=RequestMethod.GET)
    public ResponseEntity<List<Task>> listAllTasks(){
        List<Task> resList =  taskService.getAllTasks(); 
        if(resList.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(resList);
        }
        
    }

    @RequestMapping(value="/tasks/{id}/done", method=RequestMethod.PUT)
    public ResponseEntity<Void> markTask(@PathVariable int id){
        boolean resVal = taskService.markTask(id);
        if(resVal){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/tasks/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTask(@PathVariable int id){
        boolean resVal = taskService.deleteTask(id);
        if(resVal){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
