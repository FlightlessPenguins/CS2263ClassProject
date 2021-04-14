package edu.isu.cs.cs2263.todoListManager.controller;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.context.TaskContext;
import edu.isu.cs.cs2263.todoListManager.model.context.TaskListContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.*;
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
    private TaskList unfilteredUIList = new TaskList(0);
    private TaskList filteredUIList = new TaskList(1);

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
        return filteredUIList;
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
        Write.writeAllAccountsToFile();
        AccountContext.CURRENT_ACCOUNT = NullAccount.instance();
        /**
         * Need to make this take the user back to the home screen as well
         */
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

    public void readData() {
        ((AccountListState)AccountListState.instance()).setAccounts(Read.readAllUserData());
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

        // I'd recommend handling this similarly to the TaskList's search(), as far as cycling through things.
        // Note: The iterators only cycle through Tasks, can't use them.

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
        if (currentAccount != null) currentAccount.setPassword(password);

    }

    public void searchTasks(String searchTerm) {
        Account user = ((AccountContext)(AccountContext.instance())).getCurrentAccount();
        if (user instanceof UserAccount) {
            unfilteredUIList = ((UserAccount) user).getTaskLists().search(searchTerm);
        }
        else unfilteredUIList = new TaskList(0);
    }

    public void filterTasks(List<String> filters) {
        Account user = ((AccountContext)(AccountContext.instance())).getCurrentAccount();
        if (user instanceof UserAccount) {
            /*
            UNCOMMENT THIS ONCE FILTER() HAS BEEN ADDED TO TASKLIST CLASS (and remove the exception below).
            Check out TaskList's search for inspiration. You can actually (as a bare minimum) just search for the filter, depending on how much we have implemented.
            unfilteredUIList = ((UserAccount) user).getTaskLists().filter(filters);
             */
        }
        else unfilteredUIList = new TaskList(0);
        throw new RuntimeException("TaskList.filter() not implemented yet.");
    }

    /**
     * called by UI.
     * saves data, logs user out, shuts down system.
     *
     * @author Grant Baird
     */
    public void close() {
        instance().logout();
        System.exit(0);
    }


    public void createTaskList(String name, String comment) {
        throw new RuntimeException("not implemented yet.");
    }

    public Account getCurrentUser() {
        return ((AccountContext)(AccountContext.instance())).getCurrentAccount();
    }

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
        ((UserAccount)(getCurrentUser())).getTask(taskID).setDueDate(newDueDate);
    }

    public void showTasks(TaskList taskList) {throw new RuntimeException("not implemented yet.");}
    public void showTaskListInfo(TaskList taskList) {throw new RuntimeException("not implemented yet.");}
    public void ShowTaskInfo(int taskID) {throw new RuntimeException("not implemented yet.");}
    public void editTask(int taskID) {throw new RuntimeException("not implemented yet.");}



    /**
     * creates a task.
     *
     * @param title (String) title of task
     * @param description (String) description of task
     * @param labels (List<String>) List of labels applied to task
     * @param dueDate (Calendar) desired due date
     * @param dateCompleted (Calendar) date completed. empty/null if incomplete
     * @param subtasks (List<Task>) List of subtasks. Null if already a subtask
     * @param desiredTaskList (TaskList) desired TaskList that will hold the Task. Null and it will be in the
     *
     * @author Grant Baird
     */
    public Task createTask(String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, List<Task> subtasks, TaskList desiredTaskList, Section desiredTaskSection) {
        UserAccount account = (UserAccount)((AccountContext)AccountContext.instance()).getCurrentAccount();
        Task newTask = new Task();
        //Task newTask = new Task(title, description, labels, dueDate, dateCompleted, subtasks, parentTaskID)
        if(title != null && description != null && labels != null && dueDate != null && dateCompleted != null && subtasks != null) {
            newTask = new Task(title, description, labels, dueDate, dateCompleted, subtasks);
        }
        else if(title != null && description != null) {
            newTask = new Task(title, description);
        }
        else if(title != null) {
            newTask = new Task(title);
        }

        if (desiredTaskList != null && desiredTaskSection == null) {
            account.getTaskLists().addTask(newTask);
        }
        else if (desiredTaskList == null && desiredTaskSection == null) {
            account.getTaskLists().addTask(newTask);
        }
        else if (desiredTaskList != null && desiredTaskSection != null) {
            account.getTaskLists().getSections().get(desiredTaskSection.getID()).addTask(newTask);
        }
        else if (desiredTaskList == null && desiredTaskSection != null) {
            //account.getTaskLists().getSections().;
        }


        return newTask;
    }



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
