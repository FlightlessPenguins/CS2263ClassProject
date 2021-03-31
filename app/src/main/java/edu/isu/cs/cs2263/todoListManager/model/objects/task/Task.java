/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.task;

import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;
import edu.isu.cs.cs2263.todoListManager.storage.Read;

import java.util.Calendar;
import java.util.List;

public class Task implements Searchable {

    private int id;
    private String title;
    private String description;
    private List<String> labels;
    private Calendar dueDate;
    private Calendar dateCompleted;
    private List<Task> subtasks;
    private int parentTaskID;
    private int parentSectionID;

    public Task(int id, String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, List<Task> subtasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.labels = labels;
        this.dueDate = dueDate;
        this.dateCompleted = dateCompleted;
        this.subtasks = subtasks;
    }

    public Task(String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, List<Task> subtasks) {
        this(Read.readNextID("task"), title, description, labels, dueDate, dateCompleted, subtasks);
    }

    public Task(String title, String description) {
        this(Read.readNextID("task"), title, description, null, null, null, null);
    }

    public Task() {

    }

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
     * @return (List < Task >) List of Tasks matching the visitor's search criteria.
     */
    @Override
    public List<Task> accept(SearchVisitor v) {
        throw new RuntimeException("accept not implemented, yet.");
    }
}
