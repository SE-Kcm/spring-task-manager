package com.taskManager.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @Test
    public void addTask_validName(){
        Task mockTask = new Task("Task 1", Task.Status.TODO);
        mockTask.setId(1);
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        Task res = taskService.addTask("Task 1");
        assertNotNull(res);
        assertEquals("Task 1", res.getName());
        assertEquals(1, res.getId());
        assertEquals(Task.Status.TODO, res.getStatus());
    }

    @Test
    public void addTask_veryLongName(){
        String taskName = "Taskkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
        Task mockTask = new Task(taskName, Task.Status.TODO);
        mockTask.setId(2);
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        Task res = taskService.addTask(taskName);
        assertNotNull(res);
        assertEquals(taskName, res.getName());
        assertEquals(2, res.getId());
        assertEquals(Task.Status.TODO, res.getStatus());
    }

    @Test
    public void getAllTasks_ListNotEmpty(){
        Task mock1 = new Task("Task1", Task.Status.TODO);
        mock1.setId(1);
        Task mock2 = new Task("Task2", Task.Status.TODO);
        mock2.setId(2);
        List<Task> mockList = new LinkedList<Task>();
        mockList.add(mock1);
        mockList.add(mock2);

        when(taskRepository.findAll()).thenReturn(mockList);

        List<Task> res = taskService.getAllTasks();

        assertEquals(res, mockList);

    }

        @Test
    public void getAllTasks_ListEmpty(){
        List<Task> mockList = new LinkedList<Task>();

        when(taskRepository.findAll()).thenReturn(mockList);

        List<Task> res = taskService.getAllTasks();

        assertEquals(res, mockList);

    }

    @Test
    public void deleteTask_idPresent(){
        Task mockTask = new Task("Task 1", Task.Status.TODO);
        int id = 1;
        mockTask.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(mockTask));

        taskService.deleteTask(id);

    }

      @Test
    public void deleteTask_idNotPresent(){
        int id = 99;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,() -> taskService.deleteTask(id));

    }

    @Test
    public void markTask_idPresent(){
        Task mockTask = new Task("Task 1", Task.Status.TODO);
        int id = 1;
        mockTask.setId(id);
        
        when(taskRepository.findById(id)).thenReturn(Optional.of(mockTask));

        taskService.markTask(id);
        
        assertEquals(Task.Status.DONE, mockTask.getStatus());
    }

        @Test
    public void markTask_idNotPresent(){
        int id = 99;
        
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,() -> taskService.markTask(id));
        
        
    }

}
