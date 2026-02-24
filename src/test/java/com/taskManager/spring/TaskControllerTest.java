package com.taskManager.spring;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;

/*
No @Mock and @InjectMocks because that would only verify the Java logic 
With @WebMvcTest and @MockMvc we can test the "web-specific" parts:
    1. Ensures URLs like "/tasks" are correctly registered
    2. Verifies that Java objects are correctly serialized into JSON (via Jackson).
    3. Validates that the API returns the correct responses (e.g., 200 OK, 201 Created, or 400 Bad Request).
 * */

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper; 
    
    @MockitoBean //Unlike @Mock, automatically injected into the controller
    TaskService taskService;

    @Test
    public void addTask_success() throws Exception{
        String taskName = "Task1";

        AddTaskRequestDTO request = new AddTaskRequestDTO(taskName);

        Task mockTask = new Task(taskName, Task.Status.TODO);
        mockTask.setId(1);

        when(taskService.addTask(taskName)).thenReturn(mockTask);

        mockMvc.perform(post("/tasks")
            .contentType(MediaType.APPLICATION_JSON) //inform server that we are sending JSON
            .content(objectMapper.writeValueAsString(request))) //in real-life: the client sends data as a JSON String --> to avoid writing JSON-code into String variable use objectMapper; converts Java object into String
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value(taskName))
            .andExpect(jsonPath("$.status").value("TODO")) //Enums are usually serialized as Strings in JSON
            .andExpect(jsonPath("$.message").value("Task added successfully!"));
    }

    @Test
    public void addTask_failureEmptyName() throws Exception{
        String taskName = "";
        AddTaskRequestDTO request = new AddTaskRequestDTO(taskName);

        mockMvc.perform(post("/tasks")
            .contentType(MediaType.APPLICATION_JSON) 
            .content(objectMapper.writeValueAsString(request))) 
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Invalid input!"));
    }

    @Test
    public void getAllTasks_success() throws Exception{
        Task task1 = new Task("Task1", Task.Status.TODO);
        task1.setId(1);
        Task task2 = new Task("Task2", Task.Status.DONE);
        task2.setId(2);
        List<Task> mockList = new LinkedList<Task>();
        mockList.add(task1);
        mockList.add(task2);

        when(taskService.getAllTasks()).thenReturn(mockList);

        mockMvc.perform(get("/tasks"))                                          //simulates HTTP Get request
            .andExpect(status().isOk())                                                     //expected status is 200 OK
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))                   //expected content type is JSON
            .andExpect(jsonPath("$.length()").value(2))      
            .andExpect(jsonPath("$[0].name").value("Task1"))     //check content of JSON 
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].status").value("TODO"))
            .andExpect(jsonPath("$[1].name").value("Task2"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].status").value("DONE"));

    }

    @Test
    public void getAllTasks_successEmptyList() throws Exception{
        List<Task> mockList = new LinkedList<Task>();
        when(taskService.getAllTasks()).thenReturn(mockList);

        mockMvc.perform(get("/tasks")).andExpect(status().isNoContent()); //Why is there no JSON validation as in method getAllTasks_success? --> The Controller returns status 2024 No Content when the list is empty --> there is no body --> there is neither a content type nor JSON data
    }

    @Test
    public void markTask_success() throws Exception{
        int id = 1;

        mockMvc.perform(put("/tasks/{id}/done",id)) //we have to tell MockMvc what the value for {id} is
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
            .andExpect(jsonPath("$.message").value("Task marked as DONE!"));
    }

       @Test
    public void markTask_failure() throws Exception{
        int id = 99;
        doThrow(new TaskNotFoundException(id)).when(taskService).markTask(id);

        mockMvc.perform(put("/tasks/{id}/done",id)) 
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
            .andExpect(jsonPath("$.message").value("Task with ID 99 could not be found!"));
    }

        @Test
    public void deleteTask_success() throws Exception{
        int id = 1;

        mockMvc.perform(delete("/tasks/{id}",id))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
            .andExpect(jsonPath("$.message").value("Task deleted successfully!"));
    }

       @Test
    public void deleteTask_failure() throws Exception{
        int id = 99;
        doThrow(new TaskNotFoundException(id)).when(taskService).deleteTask(id);

        mockMvc.perform(delete("/tasks/{id}",id)) 
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
            .andExpect(jsonPath("$.message").value("Task with ID 99 could not be found!"));
    }




}
