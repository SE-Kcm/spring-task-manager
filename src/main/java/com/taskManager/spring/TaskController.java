package com.taskManager.spring;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController implements CommandLineRunner{

    TaskService taskService;
    
    public TaskController(TaskService taskService) { 
        this.taskService = taskService;
    }


    public void start(){
        Scanner scanner = new Scanner(System.in);
    
        while(true){
            System.out.println("----------Task Manager----------");
            System.out.println("1 - Add Task\n" + //
                        "2 - List Tasks\n" + //
                        "3 - Mark Task as Done\n" + //
                        "4 - Delete Task\n" + //
                        "5 - Exit");
            System.out.println("Enter an option:");
            String userInput = scanner.nextLine();

            if(userInput.equals("1")){
                System.out.println("Enter Task name:");
                String taskName = scanner.nextLine();
                taskService.addTask(taskName);
                System.out.println("New Task was added successfully.");
            } else if (userInput.equals("2")){
                List<Task> tasks = taskService.getAllTasks();
                if(tasks.isEmpty()){
                     System.out.println("No tasks found!");
                }else{
                    for(Task t : tasks){
                        System.out.println(t.getId() + " - " + t.getName() + " - " + t.getStatus());
                    }
                }
            }else if (userInput.equals("3")){
                System.out.println("Please enter the ID of the task you want to mark as DONE.");
                int taskId = readInt(scanner);
                while(taskId < 0){
                    System.out.println("The id must be positive!");
                    taskId = readInt(scanner);
                } 
                if(taskService.markTask(taskId)){
                    System.out.println("Task marked successfully!");
                }else{
                    System.out.println("Task not found!"); 
                }
            }else if (userInput.equals("4")){
                System.out.println("Please enter the ID of the task you want to delete.");
                int taskId = readInt(scanner);
                while(taskId < 0){
                    System.out.println("The id must be positive!");
                    taskId = readInt(scanner);
                }
                if(taskService.deleteTask(taskId)){
                    System.out.println("Task deleted successfully!");
                }else{
                    System.out.println("Task not found!"); 
                }
                
            }else if (userInput.equals("5")){
                System.exit(0);
            }else{
                System.out.println("Invalid input. Please enter valid option:");
            }
        }  
    }


        public int readInt(Scanner scanner){
        while(true){
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }
    }


        public void run(String... args) throws Exception { 
            start();
        }

}
