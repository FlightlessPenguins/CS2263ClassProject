/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.account;

import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.storage.Read;

public class UserAccount extends Account {

    private String biography;
    private TaskList taskLists = new TaskList();

    /**
     * Creates a UserAccount
     *
     * @author Brandon Watkins
     */
    public UserAccount() {}

    /**
     * Creates a UserAccount
     *
     * @param biography (String) The user's biography.
     * @param taskLists (TaskList) The user's all-ecompassing TaskList.
     * @param email (String) The user's email address.
     * @param password (String) The user's password hash.
     * @param firstName (String) The user's first name.
     * @param lastName (String) The user's last name.
     *
     * @author Brandon Watkins
     */
    public UserAccount(String biography, TaskList taskLists, String email, String password, String firstName, String lastName) {
        this(Read.readNextID("account"), biography, taskLists, email, password, firstName, lastName);
    }

    /**
     * Creates a UserAccount. Only used for reading in from a file, or for creating a reserved TaskList.
     *
     * @param id (int) The user's ID Number.
     * @param biography (String) The user's biography.
     * @param taskLists (TaskList) The user's all-ecompassing TaskList.
     * @param email (String) The user's email address.
     * @param password (String) The user's password hash.
     * @param firstName (String) The user's first name.
     * @param lastName (String) The user's last name.
     *
     * @author Brandon Watkins
     */
    public UserAccount(int id, String biography, TaskList taskLists, String email, String password, String firstName, String lastName) {
        super(id, email, password, firstName, lastName);
        this.biography = biography;
        this.taskLists = taskLists;
        if (taskLists == null) this.taskLists = new TaskList();
        else this.taskLists = taskLists;
    }

    /**
     * Creates a UserAccount. Only used for reading in from a file, or for creating a reserved TaskList.
     *
     * @param email (String) The user's email address.
     * @param password (String) The user's password hash.
     * @param firstName (String) The user's first name.
     * @param lastName (String) The user's last name.
     *
     * @author Brandon Watkins
     */
    public UserAccount(String email, String password, String firstName, String lastName) {
        this(Read.readNextID("account"), null, new TaskList(), email, password, firstName, lastName);
    }

    /**
     * Gets the user's biography.
     *
     * @return (String) The user's biography.
     *
     * @author Brandon Watkins
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the user's biography.
     *
     * @param biography (String) The user's biography.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    public Account setBiography(String biography) {
        this.biography = biography;
        return this;
    }

    /**
     * Gets the user's TaskLists.
     *
     * @return (TaskList) The user's TaskLists.
     *
     * @author Brandon Watkins
     */
    public TaskList getTaskLists() {
        return taskLists;
    }

    /**
     * Sets the user's TaskLists.
     *
     * @param taskLists (TaskList) The user's TaskLists.
     * @return (Account) The user's account.
     *
     * @author Brandon Watkins
     */
    public Account setTaskLists(TaskList taskLists) {
        this.taskLists = taskLists;
        return this;
    }

    public Account addTaskList(TaskList taskList) {
        taskLists.addSubTaskList(taskList);
        return this;
    }

    /**
     * Determines whether another object is equal to this object.
     * @param o (Object) The object being compared to this object.
     * @return (boolean) True if the objects are deemed equal.
     */
    public boolean equals(Object o) {
        if (o instanceof UserAccount && ((UserAccount)o).getID() >= 0 && this.id == ((UserAccount)o).getID()) return true;
        return false;
    }

}
