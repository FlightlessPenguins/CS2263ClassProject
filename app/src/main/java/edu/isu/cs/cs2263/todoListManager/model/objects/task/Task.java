/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.task;

import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;
import edu.isu.cs.cs2263.todoListManager.storage.Read;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Task implements Searchable, Serializable {

    private int id;
    private String title;
    private String description;
    private List<String> labels;
    private Calendar dueDate;
    private Calendar dateCompleted;
    private List<Task> subtasks;
    private int parentTaskID;
    private int parentSectionID;

    /**
     * Creates a Task.
     * @param id (int) The task's ID number.
     * @param title (String) The task's title.
     * @param description (String) The task's description.
     * @param labels (List<String>) The task's labels/tags.
     * @param dueDate (Calendar) The date the task is due or scheduled for.
     * @param dateCompleted (Calendar) The date the task was completed.
     * @param subtasks (List<Task>) List of the task's subtasks.
     *
     * @author Brandon Watkins
     */
    public Task(int id, String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, List<Task> subtasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.labels = labels;
        this.dueDate = dueDate;
        this.dateCompleted = dateCompleted;
        this.subtasks = subtasks;
    }

    /**
     * Creates a Task.
     * @param title (String) The task's title.
     * @param description (String) The task's description.
     * @param labels (List<String>) The task's labels/tags.
     * @param dueDate (Calendar) The date the task is due or scheduled for.
     * @param dateCompleted (Calendar) The date the task was completed.
     * @param subtasks (List<Task>) List of the task's subtasks.
     *
     * @author Brandon Watkins
     */
    public Task(String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, List<Task> subtasks) {
        this(Read.readNextID("task"), title, description, labels, dueDate, dateCompleted, subtasks);
    }

    /**
     * Creates a Task.
     * @param title (String) The task's title.
     * @param description (String) The task's description.
     *
     * @author Brandon Watkins
     */
    public Task(String title, String description) {
        this(Read.readNextID("task"), title, description, null, null, null, null);
    }

    /**
     * Creates a Task.
     * @param title (String) The task's title.
     *
     * @author Brandon Watkins
     */
    public Task(String title) {
        this(Read.readNextID("task"), title, null, null, null, null, null);
    }

    /**
     * Creates a Task.
     *
     * @author Brandon Watkins
     */
    public Task() {

    }

    /**
     * Gets the task's ID number.
     * @return (int) The task's ID number.
     *
     * @author Brandon Watkins
     */
    public int getID() { return id; }

    /**
     * Gets the list of subtasks belonging to this task.
     *
     * @return (List<Task>) List of children Tasks.
     *
     * @author Brandon Watkins
     */
    public List<Task> getSubtasks() {
        return subtasks;
    }

    /**
     * Searches via the SearchVisitor, returning a List of Tasks matching the search term.
     *
     * @param v (SearchVisitor) The visitor used to search with.
     * @return (Task) Task matching the visitor's search criteria.
     */
    @Override
    public Task accept(SearchVisitor v) {
        String s = v.getSearchTerm();
        if (title.contains(s) || description().contains(s)) {
            return this;
        }
        return null;
    }

    /**
     * Determines whether another object is equal to this object.
     *
     * @param o (Object) The object being compared to this object.
     * @return (boolean) True if the objects are deemed equal.
     *
     * @author Brandon Watkins
     */
    public boolean equals(Object o) {
        if (o instanceof Task && ((Task)o).getID() >= 0 && this.id == ((Task)o).getID()) return true;
        return false;
    }

}
