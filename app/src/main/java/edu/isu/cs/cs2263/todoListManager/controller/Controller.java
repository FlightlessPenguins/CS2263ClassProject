package edu.isu.cs.cs2263.todoListManager.controller;

import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;

import java.util.List;

public class Controller {
    private static Controller singleton = null;

    private Controller() {}

    public static Controller Instance() {
        if (singleton == null) {
            singleton = new Controller();
        }
        return singleton;
    }

    private List<String> filters;

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public List<User> getUsers() {
        throw new RuntimeException("not implemented yet.");
    }

    public TaskList getUIList() {
        throw new RuntimeException("not implemented yet.");
    }

    public void updateTaskList(int ID, String title, String description, String comment, String subTaskListIDs) {
        throw new RuntimeException("not implemented yet.");
    }

    public void updateSection(int ID, String title, String description, String taskIDs) {
        throw new RuntimeException("not implemented yet.");
    }

    public User login(int ID, String password) {
        throw new RuntimeException("not implemented yet.");
    }

    public void rescheduleList(String date) {
        throw new RuntimeException("not implemented yet.");
    }

    public void sortTasks(String category, String direction) {
        throw new RuntimeException("not implemented yet.");
    }

    public void logout() {
        throw new RuntimeException("not implemented yet.");
    }

    public Boolean saveData() {
        throw new RuntimeException("not implemented yet.");
    }

    public Boolean readData() {
        throw new RuntimeException("not implemented yet.");
    }

    public void archiveList(int listID) {
        throw new RuntimeException("not implemented yet.");
    }

    public void searchTasks(String searchTerm) {throw new RuntimeException("not implemented yet.");}
    public void filterTasks(List<String> filters) {throw new RuntimeException("not implemented yet.");}
    public void close() {throw new RuntimeException("not implemented yet.");}
    public void createTaskList(String name, String comment) {throw new RuntimeException("not implemented yet.");}
    public void getCurrentUser(int currentUserID) {throw new RuntimeException("not implemented yet.");}
    public void createSection(String title, String description) {throw new RuntimeException("not implemented yet.");}
    public void resetPassword(int ID) {throw new RuntimeException("not implemented yet.");}
    public void rescheduleTask(int taskID) {throw new RuntimeException("not implemented yet.");}
    public Stage showTasks(TaskList taskList) {throw new RuntimeException("not implemented yet.");}
    public Stage showTaskListInfo(TaskList taskList) {throw new RuntimeException("not implemented yet.");}
    public Stage ShowTaskInfo(Stage stage) {throw new RuntimeException("not implemented yet.");}
    public void editTask(Stage stage) {throw new RuntimeException("not implemented yet.");}
    public void createTask(Stage stage) {throw new RuntimeException("not implemented yet.");}
    public void createSubtask(Stage stage) {throw new RuntimeException("not implemented yet.");}
    public Stage registerNew() {throw new RuntimeException("not implemented yet.");}
    public Stage changeUserInfo() {throw new RuntimeException("not implemented yet.");}
    public Stage displayLogo() {throw new RuntimeException("not implemented yet.");}

}
