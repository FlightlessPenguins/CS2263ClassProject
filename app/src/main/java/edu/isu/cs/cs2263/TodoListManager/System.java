package edu.isu.cs.cs2263.TodoListManager;



import java.util.List;

public class System {
    private static System singleton = null;

    private System() {}

    public static System Instance() {
        if (singleton == null) {
            singleton = new System();
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

    }

    public TaskList getUIList() {

    }

    public void updateTaskList(int ID, String title, String description, String comment, String subTaskListIDs) {

    }

    public void updateSection(int ID, String title, String description, String taskIDs) {

    }

    public User login(int ID, String password) {

    }

    public void rescheduleList(String date) {

    }

    public void sortTasks(String category, String direction) {

    }

    public void logout() {

    }
    public Boolean saveData() {

    }

    public Boolean readData() {

    }
    public void archiveList(int listID) {

    }
    public void searchTasks(String searchTerm) {}
    public void filterTasks(List<String> filters) {}
    public void close() {}
    public void createTaskList(String name, String comment) {}
    public void getCurrentUser(int currentUserID) {}
    public void createSection(String title, String description) {}
    public void resetPassword(int ID) {}
    public void rescheduleTask(int taskID) {}
    public Stage showTasks(TaskList taskList) {}
    public Stage showTaskListInfo(TaskList taskList) {}
    public Stage ShowTaskInfo(Stage stage) {}
    public void editTask(Stage stage) {}
    public void createTask(Stage stage) {}
    public void createSubtask(Stage stage) {}
    public Stage registerNew() {}
    public Stage changeUserInfo() {}
    public Stage displayLogo() {}




}
