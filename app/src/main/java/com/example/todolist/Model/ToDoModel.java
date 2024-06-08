package com.example.todolist.Model;

public class ToDoModel {
    private int id;
    private String task;
    private String description; // Add this field if it doesn't exist
    private int status;


    // Existing getters and setters
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    // Add getter and setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
