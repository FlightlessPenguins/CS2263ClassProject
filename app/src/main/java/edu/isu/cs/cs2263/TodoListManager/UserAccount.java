package edu.isu.cs.cs2263.TodoListManager;

public class UserAccount extends Account {

    private String biography;
    private TaskList taskLists;

    public UserAccount() {}

    public UserAccount(String biography, TaskList taskLists, String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
        this.biography = biography;
        this.taskLists = taskLists;
    }

    public UserAccount(int id, String biography, TaskList taskLists, String email, String password, String firstName, String lastName) {
        super(id, email, password, firstName, lastName);
        this.biography = biography;
        this.taskLists = taskLists;
    }

    public String getBiography() {
        return biography;
    }

    public Account setBiography(String biography) {
        this.biography = biography;
        return this;
    }

    public TaskList getTaskLists() {
        return taskLists;
    }

    public Account setTaskLists(TaskList taskLists) {
        this.taskLists = taskLists;
        return this;
    }
}
