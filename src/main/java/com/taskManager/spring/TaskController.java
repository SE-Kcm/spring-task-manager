package com.taskManager.spring;

import java.util.LinkedList;
import java.util.List;

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
    public ResponseEntity<AddTaskResponseDTO> addTask(@RequestBody AddTaskRequestDTO body){
        String taskName = body.getName();
        if(taskName != null && !taskName.trim().isEmpty()){
            Task task = taskService.addTask(taskName);
            return ResponseEntity.ok(new AddTaskResponseDTO(taskName, task.getId(), task.getStatus(), "Task added successfully!"));
        }else{
            //return ResponseEntity.badRequest().build();
            return ResponseEntity.status(400).body(new AddTaskResponseDTO("Invalid input!"));
        }
    }

    // Convert Task entities to TaskDTOs to safely expose only id, name, and status in a frontend-ready JSON
    @GetMapping(value="/tasks")
    public ResponseEntity<List<TaskDTO>> listAllTasks(){
        List<Task> resList =  taskService.getAllTasks(); 
        List<TaskDTO> dtoList = new LinkedList<TaskDTO>();
        if(resList.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            for(Task t : resList){
                dtoList.add(new TaskDTO(t.getName(),t.getId(),t.getStatus()));
            }
            return ResponseEntity.ok(dtoList);
        }
        
    }
    
    @PutMapping(value="/tasks/{id}/done")
    public ResponseEntity<MessageResponseDTO> markTask(@PathVariable int id){
        boolean resVal = taskService.markTask(id);
        if(resVal){
            return ResponseEntity.status(200).body(new MessageResponseDTO("Task marked as DONE!"));
        }else{
            return ResponseEntity.status(404).body(new MessageResponseDTO("Task could not be found!"));
        }
    }

    @DeleteMapping(value="/tasks/{id}")
    public ResponseEntity<MessageResponseDTO> deleteTask(@PathVariable int id){
        boolean resVal = taskService.deleteTask(id);
        if(resVal){
            return ResponseEntity.status(200).body(new MessageResponseDTO("Task deleted successfully!"));
        }else{
            return ResponseEntity.status(404).body(new MessageResponseDTO("Task could not be found!"));
        }
    }

}
