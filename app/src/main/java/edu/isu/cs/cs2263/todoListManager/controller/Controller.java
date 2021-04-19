package edu.isu.cs.cs2263.todoListManager.controller;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.context.TaskContext;
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
import edu.isu.cs.cs2263.todoListManager.view.Event;
import edu.isu.cs.cs2263.todoListManager.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    public void archiveList(int listID) {
        //Get current user account
        UserAccount account = (UserAccount) ((AccountContext)AccountContext.instance()).getCurrentAccount();

        //Create iterator to retrieve list
        Iterator<TaskList> iter = account.getTaskLists().getSubTaskLists().iterator();
        TaskList currentList = null;
        while(iter.hasNext()){
            currentList = iter.next();
            if(currentList.getID() == listID) break;
        }

        //archive TaskList and save
        currentList.setArchived(true);
        saveData();

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
        Account currentAccount = null;

        while(iter.hasNext()){
            currentAccount = iter.next();
            if(currentAccount.getID() == ID) break;
        }
        currentAccount.setPassword(password);
        saveData();

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
     * Creates new section and adds it to specific list
     *
     * @param listID (int)
     * @param title (String)
     * @param description (String)
     * @author Liam Andrus
     */
    public void createSection(String title, String description, int listID) {
        //Get current user account
        UserAccount account = (UserAccount) ((AccountContext)AccountContext.instance()).getCurrentAccount();

        //Create section
        Section newSection = new Section(title, description);

        //Create iterator to retrieve list
        Iterator<TaskList> iter = account.getTaskLists().getSubTaskLists().iterator();
        TaskList currentList = null;
        while(iter.hasNext()){
            currentList = iter.next();
            if(currentList.getID() == listID) break;
        }
        //Add section to list and save
        currentList.addSection(newSection);
        saveData();
    }

    /**Updates a section that belongs to the target TaskList
     *
     * @param sectionID
     * @param listID
     * @param title
     * @param desc
     * @author Liam Andrus
     */
    public void updateSection(int sectionID, int listID, String title, String desc){
        //Get current user account
        UserAccount account = (UserAccount) ((AccountContext)AccountContext.instance()).getCurrentAccount();

        //Get specific TaskList where the section is found
        Iterator<TaskList> iter = account.getTaskLists().getSubTaskLists().iterator();
        TaskList currentList = null;
        while(iter.hasNext()){
            currentList = iter.next();
            if(currentList.getID() == listID) break;
        }

        //Get specific section in the TaskLists sections
        Iterator<Section> iter2 = currentList.getSections().iterator();
        Section currentSection = null;
        while(iter2.hasNext()){
            currentSection = iter2.next();
            if(currentSection.getID() == sectionID) break;
        }
        //Update the title and description and save operations
        currentSection.setTitle(title);
        currentSection.setDescription(desc);

        saveData();

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
        Task newTask = null;
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


        return newTask;
    }


    /**Creates a subtask and adds it to target taskList
     *
     * @param title (String) title of the task
     * @param description (String) task's description
     * @param labels (List<String>) list of task's labels
     * @param dueDate (Calendar) due date of the task
     * @param dateCompleted (Calendar) date the task was completed
     * @param parentTaskID (int) ID of the task that the subtask will be added to
     * @author Liam Andrus
     */
    public void createSubtask(String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, int parentTaskID) {
        //Get current user account
        UserAccount account = (UserAccount) ((AccountContext)AccountContext.instance()).getCurrentAccount();

        //Get parent task
        Task parent = account.getTaskLists().findTask(parentTaskID);

        Task newSubTask = new Task(-13, title, description, labels, dueDate, dateCompleted, null);
        parent.addSubTask(newSubTask);

        saveData();
    }

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

    public void handle(Event event, Hashtable<String, Object> args) {
        switch(event) {
            case CreateTaskList:

                break;
            case UpdateTaskList:

                break;
            case ViewTaskList:

                break;
            case RescheduleTaskList:

                break;
            case ArchiveTaskList:

                break;
            case SortTasks:

                break;
            case FilterTasks:

                break;
            case SearchTasks:

                break;
            case CreateSection:

                break;
            case UpdateSection:

                break;
            case ViewSection:

                break;
            case CreateTask:

                break;
            case UpdateTask:

                break;
            case ViewTask:

                break;
            case Register:

                break;
            case UpdateUser:

                break;
            case ViewUser:

                break;
            case ViewListOfAllUsers:

                break;
            case Login:

                break;
            case Logout:

                break;
            case Cancel:

                break;
            case CloseApp:

                break;
            case OpenApp:

                break;
            default:
                // do nothing
                break;
        }
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
