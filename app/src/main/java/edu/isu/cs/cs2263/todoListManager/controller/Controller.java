package edu.isu.cs.cs2263.todoListManager.controller;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

<<<<<<< HEAD
public class Controller implements Initializable {
=======
//import static org.graalvm.compiler.options.OptionType.User;

public class Controller {
>>>>>>> 9b662f3a19988ca5b60094956d4afecc6692389c

    public Controller() {}

    private List<String> filters;

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public List<UserAccount> getUsers() {
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

    public UserAccount login(int ID, String password) {
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

    /**
     * called by UI.
     * saves data, logs user out, shuts down system.
     *
     * @author Grant Baird
     */
    public void close() {
        instance().saveData();
        instance().logout();
        System.exit(0);
    }
    public void createTaskList(String name, String comment) {throw new RuntimeException("not implemented yet.");}
    public UserAccount getCurrentUser() {throw new RuntimeException("not implemented yet."); }
    public void createSection(String title, String description) {throw new RuntimeException("not implemented yet.");}
    public void resetPassword(int ID) {throw new RuntimeException("not implemented yet.");}

    /**
     * reschedule task based on ID
     *
     * @author Grant Baird
     */
    public void rescheduleTask(int taskID, Calendar newDueDate) {
        getCurrentUser().getTask(taskID).setDueDate(newDueDate);

    }

    public void showTasks(TaskList taskList) {throw new RuntimeException("not implemented yet.");}
    public void showTaskListInfo(TaskList taskList) {throw new RuntimeException("not implemented yet.");}
    public void ShowTaskInfo(int taskID) {throw new RuntimeException("not implemented yet.");}
    public void editTask(int taskID) {throw new RuntimeException("not implemented yet.");}
    public void createTask(String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, List<Task> subtasks, int parentTaskID) {
        throw new RuntimeException("not implemented yet.");}
    public void createSubtask(String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, int parentTaskID) {throw new RuntimeException("not implemented yet.");}
    public void registerNew() {throw new RuntimeException("not implemented yet.");}
    public void changeUserInfo() {throw new RuntimeException("not implemented yet.");}
    public void displayLogo() {throw new RuntimeException("not implemented yet.");}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public Button loginRegisterBtn;
    public Button closeNotImplemented;

    @FXML
    private void testAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginRegisterBtn.getScene().getWindow();
        stage.close();
        View.instance().register();
    }


    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final Controller INSTANCE = new Controller();
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static Controller instance() {
        return Helper.INSTANCE;
    }
}
