package edu.isu.cs.cs2263.TodoListManager;

import org.w3c.dom.CDATASection;

import java.util.Calendar;
import java.util.List;

public class TaskList {
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

    public void setComment(String comment) {
        this.comment = comment;
    }
    public this addSubTaskList(TaskList taskList) {}

    public TaskList removeSubTaskList(int taskListID) {}

    public this addSection(Section section) {}

    public Section removeSection(int sectionID) {}

    public Boolean isArchived() {}

    public this setArchived(Boolean wantToArchive) {}

    public Boolean update(int ID, String title, String description, String comment, String subTaskListIDs) {}

    public int getNextID() {}

    public void changeDueDates(UIList uiList, Calendar date) {}

    public TaskList moveTaskToList(Task task, TaskList destination) {}

    public TaskList search(String searchTerm) {}

    public void accept(SearchVisitor v) {}



}
