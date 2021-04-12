package edu.isu.cs.cs2263.todoListManager.controller;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AccountIterator;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.storage.Write;
import edu.isu.cs.cs2263.todoListManager.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private List<String> filters;

    public Controller() {}

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    /**
     * Gets a list of all accounts, if current user is an admin.
     *
     * @return (List<Account>) List of all accounts, or an empty list if user is not an admin.
     *
     * @author Brandon Watkins
     */
    public List<Account> getUsers() {
        if (((AccountContext) AccountContext.instance()).getCurrentAccount() instanceof AdminAccount) {
            return ((AccountListState) AccountListState.instance()).getAccounts();
        }
        else return new ArrayList<Account>();
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

    /**
     * Saves the currently logged in user's data to file.
     *
     * @author Brandon Watkins
     */
    public void saveData() {
        Write.writeAccountData(((AccountContext) AccountContext.instance()).getCurrentAccount());
    }

    /**
     * Saves the specified user's data to file.
     *
     * @param accountToSave (Account) The account to save to file.
     *
     * @author Brandon Watkins
     */
    public void saveData(Account accountToSave) {
        Write.writeAccountData(accountToSave);
    }

    public Boolean readData() {
        throw new RuntimeException("not implemented yet.");
    }


    /** Retrieves the specific TaskList and archives it
     *
     *
     * @param listID
     * @author Liam Andrus
     */
    public void archiveList(int listID, int userID) {

        Boolean bool = true;
        Integer i = userID;
        String stringID = i.toString();


        Account account = ((AccountContext)AccountContext.instance()).getCurrentAccount();

        //Iterator<TaskList> iter = acct.getTaskLists().iterator();
        // - instance variable taskLists in TaskList might need to be a List<TaskList> rather than
        //   a single taskList. not sure though

    }

    /**
     * Resets the password of the target account
     *
     * @param ID
     * @param password
     * @author Liam Andrus
     */
    public void resetPassword(int ID, String password) {

        Iterator<Account> iter = getUsers().iterator();
        int target = ID;
        Account currentAccount = null;

        while(iter.hasNext()){
            currentAccount = iter.next();
            if(currentAccount.getID() == target) break;
        }
        currentAccount.setPassword(password);

    }

    public void searchTasks(String searchTerm) {
        throw new RuntimeException("not implemented yet.");
    }

    public void filterTasks(List<String> filters) {
        throw new RuntimeException("not implemented yet.");
    }

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

    /**
     * Creates new section
     *
     * @param title (String)
     * @param description (String)
     * @return newSection (Section)
     * @author Liam Andrus
     */
    public Section createSection(String title, String description) {

        Section newSection = new Section(title, description);

        return newSection;
    }

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
