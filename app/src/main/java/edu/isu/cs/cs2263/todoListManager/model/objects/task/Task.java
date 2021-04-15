/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.task;

import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;
import edu.isu.cs.cs2263.todoListManager.storage.Read;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Task implements Searchable, Serializable {

    private static final int HAS_NO_PARENT_TASK = -1;
    private static final int NO_PARENT_SECTION = -2;
    private static final int NEW_TASK_ID = -13;
    private int id;
    private String title;
    private String description;
    private List<String> labels;
    private Calendar dueDate;
    private Calendar dateCompleted;
    private List<Task> subtasks;
    private int parentTaskID = HAS_NO_PARENT_TASK;
    private int parentSectionID = NO_PARENT_SECTION;


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
        this.id = id == NEW_TASK_ID ? Read.getNextID(this) : id;
        this.title = title;
        this.description = description;
        this.labels = labels;
        this.dueDate = dueDate;
        this.dateCompleted = dateCompleted;
        for (Task subtask : subtasks) {
            addSubTask(subtask);
        }
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
        this(NEW_TASK_ID, title, description, labels, dueDate, dateCompleted, subtasks);
    }

    /**
     * Creates a Task.
     * @param title (String) The task's title.
     * @param description (String) The task's description.
     *
     * @author Brandon Watkins
     */
    public Task(String title, String description) {
        this(title, description, null, null, null, null);
    }

    /**
     * Creates a Task.
     * @param title (String) The task's title.
     *
     * @author Brandon Watkins
     */
    public Task(String title) {
        this(title, null, null, null, null, null);
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
     * Sets this task's subtasks.
     *
     * @param subtasks (List<Task>) Subtasks to add to this task.
     * @return (Task) The parent task.
     *
     * @author Brandon Watkins
     */
    public Task setSubtasks(List<Task> subtasks) {
        this.subtasks = subtasks;
        return this;
    }

    /**
     * Adds a subtask to this task.
     *
     * @param subtask (Task) The task to be added as a child.
     * @return (Task) The parent task.
     *
     * @author Brandon Watkins
     */
    public Task addSubTask(Task subtask) {
        if (canAddSubtask()) {
            if (this.subtasks == null) this.subtasks = new ArrayList<Task>();
            this.subtasks.add(subtask);
            subtask.setParentTask(this);
        }
        return this;
    }

    /**
     * Removes a subtask from this task.
     *
     * @param subtask (Task) The task to be removed from the parent task.
     * @return (Task) The parent task.
     *
     * @author Brandon Watkins
     */
    public Task removeSubTask(Task subtask) {
        subtask.setParentTask(null);
        this.subtasks.remove(subtask);
        return this;
    }

    /**
     * Determines whether the task can have children tasks.
     *
     * @return (Boolean) True if the task can have subtasks (ie. it has no parent task).
     *
     * @author Brandon Watkins
     */
    public Boolean canAddSubtask() {
        return parentTaskID == HAS_NO_PARENT_TASK;
    }

    /**
     * Sets the task's parent task's ID.
     *
     * @param parentTask (Task) The parent task.
     * @return (Task) The child task.
     *
     * @author Brandon Watkins
     */
    public Task setParentTask(Task parentTask) {
        if (parentTask != null) parentTask.addSubTask(this);
        else this.parentTaskID = HAS_NO_PARENT_TASK;
        return this;
    }

    /**
     * Sets the task's parent task's ID.
     *
     * @param id (int) The parent's ID number.
     * @return (Task) The child task.
     *
     * @author Brandon Watkins
     */
    public Task setParentTaskID(int id) {
        this.parentTaskID = id;
        return this;
    }

    /**
     * Gets the task's parent task's ID.
     * @return (int) The parent task's ID number.
     *
     * @author Brandon Watkins
     */
    public int getParentTaskID() {
        return parentTaskID;
    }

    /**
     * Sets the task's parent Section's ID.
     *
     * @param id (int) The parent's ID number.
     * @return (Task) The child task.
     *
     * @author Brandon Watkins
     */
    public Task setParentSectionID(int id) {
        this.parentSectionID = id;
        return this;
    }

    /**
     * Gets the task's parent Section's ID.
     * @return (int) The parent Section's ID number.
     *
     * @author Brandon Watkins
     */
    public int getParentSectionID() {
        return parentSectionID;
    }

    /**
     * Gets the task's due date.
     * @return (Calendar) The task's due date.
     *
     * @author Brandon Watkins
     */
    public Calendar getDueDate() {
        return dueDate;
    }

    /**
     * Sets the task's due date.
     *
     * @param dueDate (Calendar) The date to set the task's due date to.
     * @return (Task) The task being modified.
     *
     * @author Brandon Watkins
     */
    public Task setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    /**
     * Sets the due date for today's date plus the given number of days.
     *
     * @param daysToAdd (int) Number of days from now to set the due date to.
     * @return (Task) The task being modified.
     *
     * @author Brandon Watkins
     */
    public Task addDaysToDueDate(int daysToAdd) {
        if (dueDate == null) dueDate = Calendar.getInstance();
        dueDate.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return this;
    }

    /**
     * Searches via the SearchVisitor, returning a List of Tasks matching the search term.
     *
     * @param v (SearchVisitor) The visitor used to search with.
     * @return (Task) Task matching the visitor's search criteria.
     */
    @Override
    public List<Task> accept(SearchVisitor v) {
        String s = v.getSearchTerm();
        List<Task> output = new ArrayList();
        if ((title != null && title.contains(s)) || (description != null && description.contains(s))) {
            output.add(this);
            if (subtasks != null && subtasks.size() > 0) {
                for (Task task : subtasks) {
                    output.add(task);
                }
            }
        }
        else if (subtasks != null && subtasks.size() > 0) {
            for (Task task : subtasks) {
                output.addAll(task.accept(v));
            }
        }
        return output;
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

    /**
     * Sets the Task's parent Section.
     *
     * @param parentSection (Section) The containing parent of this task.
     * @return (Task) This task.
     *
     * @author Brandon Watkins
     */
    public Task setParentSection(Section parentSection) {
        if (parentSection == null) this.parentSectionID = NO_PARENT_SECTION;
        else this.parentSectionID = parentSection.getID();
        return this;
    }
}
