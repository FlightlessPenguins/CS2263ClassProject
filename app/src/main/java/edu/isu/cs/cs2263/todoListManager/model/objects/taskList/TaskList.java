/**
 * @author Brandon Watkins, ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.taskList;

import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;
import jdk.jshell.spi.ExecutionControl;

import java.util.Calendar;
import java.util.List;

public class TaskList implements Searchable {
    /* Reserved IDs:
        0: currently viewed tasklist, unfiltered
        1: currently viewed tasklist, filtered

     */

    protected int id;
    private String title;
    private String description;
    private String comment;
    private List<TaskList> subTaskLists;
    private List<Section> sections;
    private Boolean isListArchived;

    public TaskList() {}

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
        this(getNextID(), title, description, comment, null, null, false);
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
        this(getNextID(), title, description, comment, subTaskLists, sections, isListArchived);
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
        this.id = id;
        this.title = title;
        this.description = description;
        this.comment = comment;
        this.subTaskLists = subTaskLists;
        this.sections = sections;
        this.isListArchived = isListArchived;
    }

    public int getId() {
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
        subTaskLists.add(taskList);
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
        return taskList;
    }


    public TaskList addSection(Section section) {
        
    }


    public Section removeSection(int sectionID) {

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

    public int getNextID() throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("getNextID not implemented, yet."); }

    public void changeDueDates(TaskList uiList, Calendar date) {}

    public TaskList moveTaskToList(Task task, TaskList destination) throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("moveTaskToList not implemented, yet."); }

    public TaskList search(String searchTerm) throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("search not implemented, yet."); }

    public List<Task> accept(SearchVisitor v) { throw new RuntimeException("accept not implemented, yet."); }

}