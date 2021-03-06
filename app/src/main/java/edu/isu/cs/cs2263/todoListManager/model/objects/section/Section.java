/**
 * @author Brandon Watkins, ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.section;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import jdk.jshell.spi.ExecutionControl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Section implements Searchable, Serializable {

    //Instance Variables
    private static final int NO_PARENT_TASKLIST = -1;
    private int id;
    private String title;
    private String description;
    private List<Task> tasks;
    private Boolean defaultSection = false;
    private static final int NEW_SECTION_ID = -14;
    private int parentTaskListID = NO_PARENT_TASKLIST;

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
        this(NEW_SECTION_ID, null, null, null, isDefault);
    }

    /**
     * Create a new Section.
     *
     * @param title (String) The section's title.
     * @param description (String) The section's description.
     *
     * @author Brandon Watkins
     */
    public Section(String title, String description){
        this(NEW_SECTION_ID, title, description, null, false);
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
        this(NEW_SECTION_ID, title, description, tasks, isDefault);
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
        this.id = id == NEW_SECTION_ID ? Read.getNextID(this) : id;
        this.title = title == null ? "Default" : title;
        this.description = description;
        this.defaultSection = isDefault;
        if (tasks == null) this.tasks = new ArrayList<Task>();
        else this.tasks = tasks;
    }

    //Methods

    //Getters and Setters
    public int getID() {
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
        task.setParentSection(this);
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
        task.setParentSection(null);
        return task;
    }

    public Section update(int id, String title, String description, String taskIDs){
        return this;
    }

    public Section updateBasicInfo(String title, String description){
        this.title = title;
        this.description = description;
        return this;
    }

    public Section moveTaskToSection(Task task, Section destination) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("moveTaskToSection() not implemented.");
    }

    public Iterator<Task> iterator() {
        return new SectionIterator(this);
    }

    public List<Task> accept(SearchVisitor v) {
        String s = v.getSearchTerm();
        Iterator<Task> iterator = iterator();
        List<Task> tasks = new ArrayList();
        if ((title != null && title.contains(s)) || (description != null && description.contains(s))) {
            while(iterator.hasNext()) {
                tasks.add(iterator.next());
            }
        }
        else {
            while(iterator.hasNext()) {
                Task t = iterator.next();
                tasks.addAll(t.accept(v));
            }
        }
        return tasks;
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
        if (o instanceof Section && ((Section)o).getID() >= 0 && this.id == ((Section)o).getID()) return true;
        return false;
    }

    /**
     * Sets this section's parent tasklist.
     *
     * @param taskList (TaskList) This section's parent tasklist.
     * @return (Section) This child section.
     *
     * @author Brandon Watkins
     */
    public Section setParentTaskList(TaskList taskList) {
        if (taskList == null) parentTaskListID = NO_PARENT_TASKLIST;
        else parentTaskListID = taskList.getID();
        return this;
    }

    /**
     * Sets this section's parent tasklist's ID number.
     *
     * @param parentTaskListID (int) This tasklist's parent tasklist's ID number.
     * @return (Section) This child section.
     *
     * @author Brandon Watkins
     */
    public Section setParentTaskListID(int parentTaskListID) {
        this.parentTaskListID = parentTaskListID;
        return this;
    }
}
