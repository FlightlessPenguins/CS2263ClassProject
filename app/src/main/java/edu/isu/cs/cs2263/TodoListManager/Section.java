package edu.isu.cs.cs2263.TodoListManager;

import java.util.Iterator;
import java.util.List;

public class Section {

    //Instance Variables
    private int id;
    private String title;
    private String description;
    private List<Task> tasks;

    //Constructors
    public Section(){}
    public Section(int ID, String title, String description, List<Task> tasks){}

    //Methods

    //Getters and Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Section addTask(Task task){
        return this;
    }

    public Task removeTask(int taskID){
        return task;
    }

    public Section update(int ID, String title, String description, String taskIDs){
        return this;
    }

    private int getNextID(){
        return ID;
    }

    public Section moveTaskToSection(Task task, Section destination){
        return section;
    }

    public Iterator<Task> itertor(){

    }

    public void accept(SearchVisitor v){

    }





}
