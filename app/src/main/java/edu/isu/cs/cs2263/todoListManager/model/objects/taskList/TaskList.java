/**
 * @author Brandon Watkins, ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.taskList;

import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.search.SearchVisitor;
import edu.isu.cs.cs2263.todoListManager.search.Searchable;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import jdk.jshell.spi.ExecutionControl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class TaskList implements Searchable, Serializable {
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

    public TaskList() { this(null); }

    /**
     * Creates a TaskList.
     *
     * @param title (String) The TaskList's title.
     *
     * @author Brandon Watkins
     */
    public TaskList(String title) {
        this(Read.readNextID("taskList"), title, null, null, null, null, false);
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
        this(Read.readNextID("taskList"), title, description, null, null, null, false);
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
        this(Read.readNextID("taskList"), title, description, comment, null, null, false);
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
        this(Read.readNextID("taskList"), title, description, comment, subTaskLists, sections, isListArchived);
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
        return taskList;
    }


    public TaskList addSection(Section section) {
        sections.add(section);
        return this;
    }


    public Section removeSection(Section section) {
        sections.remove(section);
        return section;
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

    public TaskList search(String searchTerm) {
        SearchVisitor visitor = new SearchTaskVisitor(searchTerm);
        List<Task> tasks = accept(visitor);
        TaskList taskList = convertTasksToTaskList(tasks);
        return taskList;
    }

    private TaskList convertTasksToTaskList(List<Task> tasks) {
        TaskList output = new TaskList(0, null, null, null, null, null, false);
        for (Task task : tasks) {
            output.addTask(task);
        }
        return output;
    }

    public List<Task> accept(SearchVisitor v) {
        String s = v.getSearchTerm();
        Iterator<Task> iterator = iterator();
        List<Task> tasks = new ArrayList();
        if (title.contains(s) || comment.contains(s) || description.contains(s) {
            while(iterator.hasNext()) {
                tasks.add(iterator.next());
            }
        }
        else {
            while(iterator.hasNext()) {
                Task task = iterator.next();
                Task t = task.accept(v);
                if (t != null) tasks.add(t);
            }
        }
        for (TaskList taskList : subTaskLists) {
            tasks.add(taskList.accept(v));
        }
        for (Section section : sections) {
            tasks.add(section.accept(v));
        }
        return tasks;
    }

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

}
