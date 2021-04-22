package edu.isu.cs.cs2263.todoListManager.controller;

import edu.isu.cs.cs2263.todoListManager.controller.command.*;
import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.context.TaskContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.*;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.ErrorState;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.storage.Write;
import edu.isu.cs.cs2263.todoListManager.view.Event;
import edu.isu.cs.cs2263.todoListManager.view.View;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private Accordion accHomeTaskList;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnRegisterUser;
    @FXML
    private Button btnLoginRegister;
    @FXML
    private Button btnLoginUser;
    @FXML
    private Button btnCreateList;
    @FXML
    private Button btnCreateSection;
    @FXML
    private Button btnCreateTask;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnEditAccount;
    @FXML
    private Button btnEditList;
    @FXML
    private Button btnEditSection;
    @FXML
    private Button btnHomeRefreshList;

    @FXML
    private CheckBox cbIsListArchived;
    @FXML
    private CheckBox cbSortDirection;

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblSortBy;
    @FXML
    private Label lblEmail;

    @FXML
    private ScrollPane spHomeAllTask;

    @FXML
    private TextArea txtBiography;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtComment;
    @FXML
    private TextField txtDueDate;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtLabels;
    @FXML
    private TextField txtFilters;

    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtPasswordConfirm;


    public Controller() {}

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

    /**
     *
     * @param email
     * @param password
     * @return
     *
     * @author Brandon Watkins
     */
    public static Account login(String email, String password) {
        AccountListState als = (AccountListState)AccountListState.instance();
        als.setAccounts(Read.readAllUserData());
        AccountContext.CURRENT_ACCOUNT = NullAccount.instance();
        for (Account account : als.getAccountsBackdoor()) {
            if (account.getEmail().equals(email.trim()) && account.getPassword().equals(((AccountContext)AccountContext.instance()).generateHash(password.trim()))
            ) {
                ((AccountContext)AccountContext.instance()).setCurrentAccount(account);
                break;
            }
        }
        return AccountContext.CURRENT_ACCOUNT;
    }

    /**
     * Saves the currently logged in user's data to file.
     *
     * @author Brandon Watkins
     */
    public static void saveData() {
        Write.writeAccountData(((AccountContext) AccountContext.instance()).getCurrentAccount());
    }

    /**
     * called by UI.
     * saves data, logs user out, shuts down system.
     *
     * @author Grant Baird
     * @author Brandon Watkins
     */
    public static void close() {
        saveData();
        SystemCommand sc = new SystemCommand(Event.Logout);
        sc.execute(null);
        System.exit(0);
    }

    public Account getCurrentUser() {
        return AccountContext.CURRENT_ACCOUNT;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void openRegisterUser(ActionEvent event) {
        /*Stage stage = (Stage) btnLoginRegister.getScene().getWindow();
        stage.close();*/
        View.instance().register();
    }
    @FXML
    private void registerNewAccount() {
        View.instance().errorMsg("Not yet implemented: User create method must be finished.");

    }

    @FXML
    private void cancelStage(ActionEvent event) {
        Stage stage = (Stage) this.btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void loginUser() {
        View.instance().errorMsg("This hasn't been completed. For testing purposes, we are logging in the test user.");
        //Complete a test login here
    }

    @FXML
    private void btnCreateList(ActionEvent event) throws IOException {
        View.instance().createList();

    }

    @FXML
    private void populateTaskListAccordion(ActionEvent event) throws IOException {
        UserAccount account = (UserAccount) ((AccountContext)AccountContext.instance()).getCurrentAccount();
        Iterator<TaskList> iter =  account.getTaskLists().getSubTaskLists().iterator();
        Accordion taskListAccordion = new Accordion();
        TaskList currentList;
        Section currentSection;
        Task currentTask;
        Task currentSubTask;
        while(iter.hasNext()) {
            currentList = iter.next();
            Accordion SectionAccordion = new Accordion();
            TitledPane taskListPane = new TitledPane(currentList.getTitle(), SectionAccordion);
            Iterator<Section> iter2 = currentList.getSections().iterator();
            while (iter2.hasNext()) {
                currentSection = iter2.next();
                VBox taskVBox = new VBox();
                TitledPane taskSectionPane = new TitledPane(currentSection.getTitle(), taskVBox);
                Iterator<Task> iter3 = currentSection.getTasks().iterator();
                while (iter3.hasNext()) {
                    currentTask = iter3.next();
                    TextArea taskArea = new TextArea(currentTask.getTitle());
                    taskVBox.getChildren().add(taskArea);
                    Iterator<Task> iter4 = currentTask.getSubtasks().iterator();
                    while (iter4.hasNext()) {
                        currentSubTask = iter4.next();
                        TextArea subtaskArea = new TextArea(currentSubTask.getTitle());
                        taskVBox.getChildren().add(subtaskArea);
                    }



                }
                taskSectionPane.setContent(taskVBox);
                SectionAccordion.getPanes().add(taskSectionPane);
            }
            taskListPane.setContent(SectionAccordion);
            taskListAccordion.getPanes().add(taskListPane);
        }
        spHomeAllTask.setContent(null);
        spHomeAllTask.setContent(taskListAccordion);
    }

    public void handle(ActionEvent event) {
        switch (((Node)event.getTarget()).getId()) {
            case "btnRegisterUser":
                handle(Event.Register);
                break;
            case "btnLoginUser":
                handle(Event.Login);
                break;
            /*
            case CREATE_TASK:
                handle(Event.CreateTask);
                break;
            case CREATE_TASKLIST:
                handle(Event.CreateTaskList);
                break;
            case CREATE_SECTION:
                handle(Event.CreateSection);
                break;
            case UPDATE_TASK:
                handle(Event.UpdateTask);
                break;
            case UPDATE_TASKLIST:
                handle(Event.UpdateTaskList);
                break;
            case UPDATE_SECTION:
                handle(Event.UpdateSection);
                break;
            case UPDATE_USER:
                handle(Event.UpdateUser);
                break;
            case VIEW_USER:
                handle(Event.ViewUser);
                break;
            case VIEW_TASK:
                handle(Event.ViewTask);
                break;
            case VIEW_TASKLIST:
                handle(Event.ViewTaskList);
                break;
            case VIEW_SECTION:
                handle(Event.ViewSection);
                break;
            case RESCHEDULE_TASKLIST:
                handle(Event.RescheduleTaskList);
            case ARCHIVE_TASKLIST:
                handle(Event.ArchiveTaskList);
                break;
            case SORT_TASKS:
                handle(Event.SortTasks);
                break;
            case FILTER_TASKS:
                handle(Event.FilterTasks);
                break;
            case SEARCH_TASKS:
                handle(Event.SearchTasks);
                break;
            case VIEW_LIST_OF_ALL_USERS:
                handle(Event.ViewListOfAllUsers);
                break;
            case LOGOUT:
                handle(Event.Logout);
                break;
            case CLOSE_APP:
                handle(Event.CloseApp);
                break;
            */

            case "btnCancel":
                handle(Event.Cancel);
                break;
            case "btnLoginRegister":
                ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.RegisterForm);
                // do nothing. Event.Register is for when they've submitted their registration info
                break;
            default:
                // do nothing
                break;
        }
    }

    public void handle(Event event) {
        // All code that's been commented out MUST BE FIXED, the commands depend on them.
        // I'm hoping that we can store the actual objects in the view, so that we can access things like ID, subtasks, parent, etc.
        Command c = new SystemCommand(Event.OpenApp);
        Dictionary<String,String> args = new Hashtable<>();
        switch(event) {
            case CreateTaskList:
                c = new CreateCommand(event);
                args.put("title", txtTitle.getText());
                args.put("description", txtDescription.getText());
                args.put("comment", txtComment.getText());
                //args.put("parentTaskListID", txtTitle.getParent().getId());
                break;
            case UpdateTaskList:
                c = new UpdateCommand(event);
                //args.put("id", txtTitle.getParent().getId());
                args.put("title", txtTitle.getText());
                args.put("description", txtDescription.getText());
                args.put("comment", txtComment.getText());
                args.put("isListArchived", cbIsListArchived.isSelected() ? "true" : "false");
                //args.put("parentTaskListID", txtTitle.getParent().getParent().getId());
                break;
            case ViewTaskList:
                c = new InfoCommand(event);
                //args.put("id", lblTitle.getParent().getId());
                break;
            case RescheduleTaskList:
                c = new UpdateCommand(event);
                args.put("dueDate", txtDueDate.getText());
                //args.put("id", lblTitle.getParent().getId());
                break;
            case ArchiveTaskList:
                c = new UpdateCommand(event);
                args.put("isListArchived", "true");
                //args.put("id", lblTitle.getParent().getId());
                break;
            case SortTasks:
                c = new ViewCommand(event);
                args.put("sortCategory", lblSortBy.getText());
                args.put("sortOrder", cbSortDirection.isSelected() ? "ascending" : "descending");
                break;
            case FilterTasks:
                c = new ViewCommand(event);
                args.put("filters", txtFilters.getText());
                break;
            case SearchTasks:
                c = new ViewCommand(event);
                args.put("searchTerm", txtSearch.getText());
                break;
            case CreateSection:
                c = new CreateCommand(event);
                args.put("title", txtTitle.getText());
                args.put("description", txtDescription.getText());
                args.put("defaultSection", "false");
                //args.put("parentTaskListID", txtTitle.getParent().getId());
                break;
            case UpdateSection:
                c = new UpdateCommand(event);
                //args.put("id", txtTitle.getParent().getId());
                args.put("title", txtTitle.getText());
                args.put("description", txtDescription.getText());
                break;
            case ViewSection:
                c = new InfoCommand(event);
                //args.put("id", lblTitle.getParent().getId());
                break;
            case CreateTask:
                c = new CreateCommand(event);
                args.put("title", txtTitle.getText());
                args.put("description", txtDescription.getText());
                args.put("labels", txtLabels.getText());
                args.put("dueDate", txtDueDate.getText());
                //args.put("parentTaskID", PARENT_TASK_ID);
                //args.put("parentSectionID", lblTitle.getParent().getId());//would only work if creating from a section's view
                break;
            case UpdateTask:
                c = new UpdateCommand(event);
                //args.put("id", txtTitle.getParent().getId());
                args.put("title", txtTitle.getText());
                args.put("description", txtDescription.getText());
                args.put("labels", txtLabels.getText());
                args.put("dueDate", txtDueDate.getText());
                //args.put("parentTaskID", PARENT_TASK_ID);
                //args.put("parentSectionID", PARENT_SECTION_ID);
                break;
            case ViewTask:
                c = new InfoCommand(event);
                //args.put("taskID", lblTitle.getParent().getId());
                break;
            case Register:
                c = new CreateCommand(event);
                args.put("email", txtEmail.getText());
                args.put("password", txtPassword.getText());
                args.put("confirmPassword", txtPasswordConfirm.getText());
                args.put("firstName", txtFirstName.getText());
                args.put("lastName", txtLastName.getText());
                args.put("biography", txtBiography.getText());
                break;
            case UpdateUser:
                c = new UpdateCommand(event);
                //args.put("id", lblEmail.getParent().getId());
                args.put("email", txtEmail.getText());
                args.put("password", txtPassword.getText());
                args.put("confirmPassword", txtPasswordConfirm.getText());
                args.put("firstName", txtFirstName.getText());
                args.put("lastName", txtLastName.getText());
                args.put("biography", txtBiography.getText());
                break;
            case ViewUser:
                c = new InfoCommand(event);
                args.put("email", txtEmail.getText());
                break;
            case ViewListOfAllUsers:
                c = new ListCommand(event);
                break;
            case Login:
                c = new SystemCommand(event);
                args.put("email", txtEmail.getText());
                args.put("password", txtPassword.getText());
                break;
            default:
                c = new SystemCommand(event);
                break;
        }
        c.execute(args);
        // if we are using multiple windows - if event == cancel, close window here.
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






    /* ***********************************************************************************************
     ********************************** Old methods, kept for reminders ******************************
     ************************************************************************************************/


    private List<String> filters;

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public TaskList getUIList() {
        throw new RuntimeException("not implemented yet.");
    }

    public static void logout() {
        throw new RuntimeException("not implemented yet.");
    }

    public void updateTaskList(int ID, String title, String description, String comment, String subTaskListIDs) {
        throw new RuntimeException("not implemented yet.");
    }

    public void updateSection(int ID, String title, String description, String taskIDs) {
        throw new RuntimeException("not implemented yet.");
    }

    public void rescheduleList(String date) {
        throw new RuntimeException("not implemented yet.");
    }

    public void sortTasks(String category, String direction) {
        throw new RuntimeException("not implemented yet.");
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

    public void createTaskList(String name, String comment) {throw new RuntimeException("not implemented yet.");}

    public void changeUserInfo() {throw new RuntimeException("not implemented yet.");}

    public void displayLogo() {throw new RuntimeException("not implemented yet.");}

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
     * @author Brandon Watkins
     */
    public void rescheduleTask(int taskID, Calendar newDueDate) {
        Account account = getCurrentUser();
        if (account instanceof UserAccount) ((UserAccount) getCurrentUser()).getTask(taskID).setDueDate(newDueDate);
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

    /**Creates a subtask and adds it to target taskList //WIP
     *
     * @param title
     * @param description
     * @param labels
     * @param dueDate
     * @param dateCompleted
     * @param parentTaskID
     * @author Liam Andrus
     */
    public void createSubtask(String title, String description, List<String> labels, Calendar dueDate, Calendar dateCompleted, int parentTaskID) {
        //Get current user account
        UserAccount account = (UserAccount) ((AccountContext)AccountContext.instance()).getCurrentAccount();

        Task newSubTask = new Task(title, description);
        newSubTask.setDueDate(dueDate);
    }




}
