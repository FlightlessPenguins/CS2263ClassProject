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
    public TaskList(int ID, String title, String description, String comment, List<TaskList> subTaskLists) {}

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

    public TaskList addSubTaskList(TaskList taskList) throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("addSubTaskList not implemented, yet."); }

    public TaskList removeSubTaskList(int taskListID) throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("removeSubTaskList not implemented, yet."); }

    public TaskList addSection(Section section) throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("addSection not implemented, yet."); }

    public Section removeSection(int sectionID) throws ExecutionControl.NotImplementedException { throw new ExecutionControl.NotImplementedException("removeSection not implemented, yet."); }

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
