/**
 * @author Brandon Watkins, ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.taskList;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.SearchTaskVisitor;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import jdk.jshell.spi.ExecutionControl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class
TaskList implements Searchable, Serializable {
    /* Reserved IDs:
        0: currently viewed tasklist, unfiltered
        1: currently viewed tasklist, filtered

     */

    private static final int NEW_TASKLIST_ID = -12;
    private static final int NO_PARENT_TASKLIST = -1;
    protected int id;
    private String title;
    private String description;
    private String comment;
    private List<TaskList> subTaskLists;
    private List<Section> sections;
    private Boolean isListArchived;
    private int parentTaskListID = NO_PARENT_TASKLIST;


    public TaskList() { this(null); }

    public TaskList(int id) {
        this(id, null, null, null, null, null, false);
    }

    /**
     * Creates a TaskList.
     *
     * @param title (String) The TaskList's title.
     *
     * @author Brandon Watkins
     */
    public TaskList(String title) {
        this(title, null, null, null, null, false);
    }

    /**
     * Creates a TaskList.
     *
     * @param title (String) The TaskList's title.
     * @param description (String) The TaskList's description.
     *
     * @author Brandon Watkins
     */
    public TaskList(String title, String description) {
        this(title, description, null, null, null, false);
    }

    /**
     * Creates a TaskList.
     *
     * @param title (String) The TaskList's title.
     * @param description (String) The TaskList's description.
     * @param comment (String) The TaskList's comment.
     *
     * @author Brandon Watkins
     */
    public TaskList(String title, String description, String comment) {
        this(title, description, comment, null, null, false);
    }

    /**
     * Creates a TaskList.
     *
     * @param title (String) The TaskList's title.
     * @param description (String) The TaskList's description.
     * @param comment (String) The TaskList's comment.
     * @param subTaskLists (List<TaskList>) The TaskList's children TaskLists.
     * @param sections (List<Section>) The TaskList's sections.
     * @param isListArchived (Boolean) Whether the TaskList is archived.
     *
     * @author Brandon Watkins
     */
    public TaskList(String title, String description, String comment, List<TaskList> subTaskLists, List<Section> sections, Boolean isListArchived) {
        this(NEW_TASKLIST_ID, title, description, comment, subTaskLists, sections, isListArchived);
    }

    /**
     * Creates a TaskList.
     *
     * @param id (int) The TaskList's ID number.
     * @param title (String) The TaskList's title.
     * @param description (String) The TaskList's description.
     * @param comment (String) The TaskList's comment.
     * @param subTaskLists (List<TaskList>) The TaskList's children TaskLists.
     * @param sections (List<Section>) The TaskList's sections.
     * @param isListArchived (Boolean) Whether the TaskList is archived.
     *
     * @author Brandon Watkins
     */
    public TaskList(int id, String title, String description, String comment, List<TaskList> subTaskLists, List<Section> sections, Boolean isListArchived) {
        this.id = id == NEW_TASKLIST_ID ? Read.getNextID(this) : id;
        this.title = title == null ? "Default" : title;
        this.description = description;
        this.comment = comment;
        this.subTaskLists = subTaskLists;
        this.isListArchived = isListArchived;
        if (sections == null) {
            List<Section> sects = new ArrayList();
            sects.add(new Section(true));
            this.sections = sects;
        }
        else this.sections = sections;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }

    public List<TaskList> getSubTaskLists() {
        return subTaskLists;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComment(String comment) { this.comment = comment; }

    /**
     * Adds a child TaskList to this TaskList.
     *
     * @param taskList (TaskList) The TaskList to add to this TaskList.
     * @return (TaskList) This TaskList (parent).
     *
     * @author Brandon Watkins
     */
    public TaskList addSubTaskList(TaskList taskList) {
        if (subTaskLists == null) subTaskLists = new ArrayList<TaskList>();
        subTaskLists.add(taskList);
        taskList.setParentTaskList(this);
        return this;
    }

    /**
     * Removes a child TaskList to this TaskList.
     *
     * @param taskList (TaskList) The TaskList to remove from this TaskList.
     * @return (TaskList) The TaskList that was removed.
     *
     * @author Brandon Watkins
     */
    public TaskList removeSubTaskList(TaskList taskList)  {
        subTaskLists.remove(taskList);
        taskList.setParentTaskList(null);
        return taskList;
    }

    /**
     * Adds a section to this TaskList.
     *
     * @param section (Section) The section to add to this TaskList.
     * @return (TaskList) This TaskList.
     *
     * @author Brandon Watkins
     */
    public TaskList addSection(Section section) {
        sections.add(section);
        section.setParentTaskList(this);
        return this;
    }

    /**
     * Removes a section from this tasklist.
     *
     * @param section (Section) The section to be removed.
     * @return (Section) The section that was removed.
     *
     * @author Brandon Watkins
     */
    public Section removeSection(Section section) {
        sections.remove(section);
        section.setParentTaskList(null);
        return section;
    }

    /**
     * Sets the parent tasklist for this tasklist.
     *
     * @param parentTaskList (TaskList) This tasklist's parent tasklist.
     * @return (TaskList) The child tasklist.
     *
     * @author Brandon Watkins
     */
    public TaskList setParentTaskList(TaskList parentTaskList) {
        if (parentTaskList == null) parentTaskListID = NO_PARENT_TASKLIST;
        else parentTaskListID = parentTaskList.getID();
        return this;
    }

    /**
     * Sets this tasklist's parent's tasklist's ID.
     *
     * @param parentTaskListID (int) The parent tasklist's ID number.
     * @return (TaskList) The child Tasklist.
     *
     * @author Brandon Watkins
     */
    public TaskList setParentTaskListID(int parentTaskListID) {
        this.parentTaskListID = parentTaskListID;
        return this;
    }

    /**
     * Gets this tasklist's parent tasklist's ID.
     *
     * @return (int) The parent TaskList's ID number.
     *
     * @author Brandon Watkins
     */
    public int getParentTaskListID() {
        return parentTaskListID;
    }

    /**
     * Adds the given Task to the default section of this TaskList.
     *
     * @param task (Task) The task to add.
     * @return (TaskList) This TaskList.
     *
     * @author Brandon Watkins
     */
    public TaskList addTask(Task task) {
        for (Section section : sections) {
            if (section.isDefault()) {
                section.addTask(task);
                return this;
            }
        }
        if (sections.size() > 0) {
            sections.get(0).addTask(task);
        }
        return this;
    }

    /**
     * Removes the given Task from the default section of this TaskList.
     *
     * @param task (Task) The task to be removed.
     * @return (Task) The task that was removed.
     *
     * @author Brandon Watkins
     */
    public Task removeTask(Task task) {
        for (Section section : sections) {
            if (section.isDefault()) {
                section.removeTask(task);
                return task;
            }
        }
        if (sections.size() > 0) {
            sections.get(0).removeTask(task);
        }
        return task;
    }

    /**
     * Determine whether the TaskList is currently archived.
     *
     * @return (Boolean) True if the TaskList is currently archived.
     *
     * @author Brandon Watkins
     */
    public Boolean isArchived() { return isListArchived; }

    /**
     * Sets whether or not the TaskList is to be archived.
     *
     * @param wantToArchive (Boolean) True if TaskList is to be archived.
     * @return (TaskList) The TaskList in question.
     *
     * @author Brandon Watkins
     */
    public TaskList setArchived(Boolean wantToArchive) {
        isListArchived = wantToArchive;
        return this;
    }

    public Boolean update(int ID, String title, String description, String comment, String subTaskListIDs) throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("update not implemented, yet."); }

    public void changeDueDates(TaskList uiList, Calendar date) {}

    public TaskList moveTaskToList(Task task, TaskList destination) throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("moveTaskToList not implemented, yet."); }

    /**
     * Searches through the tasklist, it's sub lists, sections, tasks, and subtasks, for the given search term.
     * @param searchTerm (String) The term to search for.
     * @return (TaskList) A new TaskList containing all matching terms and their children.
     *
     * @author Brandon Watkins
     */
    public TaskList search(String searchTerm) {
        SearchVisitor visitor = new SearchTaskVisitor(searchTerm);
        List<Task> tasks = accept(visitor);
        TaskList taskList = convertTasksToTaskList(tasks);
        return taskList;
    }

    /**
     * Converts a list of tasks into a TaskList.
     *
     * @param tasks (List<Task>) List of tasks to convert into a TaskList.
     * @return (TaskList) A new TaskList containing the converted tasks.
     *
     * @author Brandon Watkins
     */
    private TaskList convertTasksToTaskList(List<Task> tasks) {
        TaskList output = new TaskList(0, null, null, null, null, null, false);
        for (Task task : tasks) {
            output.addTask(task);
        }
        return output;
    }

    /**
     * Searches through the tasklist, it's sub lists, sections, tasks, and subtasks, for the given search term.
     *
     * @param v (SearchVisitor) The visitor used to search with.
     * @return (List<Task>) List of all tasks matching (or belonging to something that does) the search term.
     *
     * @author Brandon Watkins
     */
    public List<Task> accept(SearchVisitor v) {
        String s = v.getSearchTerm();
        Iterator<Task> iterator = iterator();
        List<Task> tasks = new ArrayList();
        if ((title != null && title.contains(s)) || (comment != null && comment.contains(s)) || (description != null && description.contains(s))) {
            while(iterator.hasNext()) {
                tasks.add(iterator.next());
            }
        }
        else {
            while(iterator.hasNext()) {
                Task task = iterator.next();
                tasks.addAll(task.accept(v));
            }
        }
        if (subTaskLists != null) {
            for (TaskList taskList : subTaskLists) {
                tasks.addAll(taskList.accept(v));
            }
        }
        if (sections != null) {
            for (Section section : sections) {
                tasks.addAll(section.accept(v));
            }
        }
        Boolean[] taskIncluded = new Boolean[1000000000];
        List<Task> toBeDeleted = new ArrayList();
        for (Task task : tasks) {
            if (taskIncluded[task.getID()] == null || !taskIncluded[task.getID()]) {
                taskIncluded[task.getID()] = true;
            }
            else {
                toBeDeleted.add(task);
            }
        }
        for (Task task : toBeDeleted) {
            tasks.remove(task);
        }
        return tasks;
    }

    /**
     * Creates an iterator to cycle through all of this TaskList's Tasks.
     *
     * @return (Iterator<Task>) Iterator of Tasks within this TaskList.
     *
     * @author Brandon Watkins
     */
    public Iterator<Task> iterator() {
        return new TaskListIterator(this);
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
        if (o instanceof TaskList && ((TaskList)o).getID() >= 0 && this.id == ((TaskList)o).getID()) return true;
        return false;
    }

    public TaskList findTaskList(int tasklistID) {
        if (this.id == tasklistID) return this;
        for (TaskList taskList : subTaskLists) {
            return taskList.findTaskList(tasklistID);
        }
        return null;
    }

    public Section findSection(int sectionID) {
        for (TaskList taskList : subTaskLists) {
            for (Section section : taskList.getSections()) {
                if (section.getID() == sectionID) return section;
            }
        }
        return null;
    }

    public Task findTask(int taskID) {
        for (TaskList taskList : subTaskLists) {
            for (Section section : taskList.getSections()) {
                for (Task task : section.getTasks()) {
                    if (task.getID() == taskID) return task;
                }
            }
        }
        return null;
    }

}
