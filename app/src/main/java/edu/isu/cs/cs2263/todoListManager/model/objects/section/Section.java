/**
 * @author ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.section;

import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;
import jdk.jshell.spi.ExecutionControl;

import java.util.Iterator;
import java.util.List;

public class Section implements Searchable {

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

    public Task removeTask(int taskID) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("removeTask() not implemented.");
    }

    public Section update(int id, String title, String description, String taskIDs){
        return this;
    }

    private int getNextID() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("getNextID() not implemented.");
    }

    public Section moveTaskToSection(Task task, Section destination) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("moveTaskToSection() not implemented.");
    }

    public Iterator<Task> iterator() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("iterator() not implemented.");
    }

    public List<Task> accept(SearchVisitor v) {
        throw new RuntimeException("accept not implemented, yet.");
    }

}
