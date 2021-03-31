/**
 * @author Brandon Watkins, ?
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
    private Boolean defaultSection = false;

    //Constructors
    public Section(){}

    /**
     * Create a new Section.
     *
     * @param isDefault (Boolean) Whether this is the nameless default section for a TaskList.
     *
     * @author Brandon Watkins
     */
    public Section(Boolean isDefault) {
        this(null, null, null, isDefault);
    }

    /**
     * Create a new Section.
     *
     * @param id (int) The section's ID number.
     * @param title (String) The section's title.
     * @param description (String) The section's description.
     * @param tasks (List<Task>) The section's contained Tasks.
     *
     * @author Brandon Watkins
     */
    public Section(int id, String title, String description, List<Task> tasks){
        this(id, title, description, tasks, false);
    }

    /**
     * Create a new Section.
     *
     * @param title (String) The section's title.
     * @param description (String) The section's description.
     * @param tasks (List<Task>) The section's contained Tasks.
     * @param isDefault (Boolean) Whether this is the nameless default section for a TaskList.
     *
     * @author Brandon Watkins
     */
    public Section(String title, String description, List<Task> tasks, Boolean isDefault){
        this(getNextID(), title, description, tasks, isDefault);
    }

    /**
     * Create a new Section.
     *
     * @param id (int) The section's ID number.
     * @param title (String) The section's title.
     * @param description (String) The section's description.
     * @param tasks (List<Task>) The section's contained Tasks.
     * @param isDefault (Boolean) Whether this is the nameless default section for a TaskList.
     *
     * @author Brandon Watkins
     */
    public Section(int id, String title, String description, List<Task> tasks, Boolean isDefault){
        this.id = id;
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.defaultSection = isDefault;
    }

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

    public void setDefaultSection(Boolean def) {
        defaultSection = def;
    }

    public Boolean getDefaultSection() {
        return defaultSection;
    }

    public Boolean isDefault() {
        return getDefaultSection();
    }

    /**
     * Adds the given task to this section.
     *
     * @param task (Task) The task to be added.
     * @return (Section) This section.
     *
     * @author Brandon Watkins
     */
    public Section addTask(Task task){
        tasks.add(task);
        return this;
    }

    /**
     * Removes the given task from this section.
     *
     * @param task (Task) The task to be removed.
     * @return (Task) The task that was removed.
     *
     * @author Brandon Watkins
     */
    public Task removeTask(Task task) {
        tasks.remove(task);
        return task;
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
