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
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private Accordion accHomeTaskList;

    @FXML
    public AnchorPane apHomeAllTask;

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
    private Button btnFinishCreateList;
    @FXML
    private Button btnFinishCreateSection;
    @FXML
    private Button btnFinishCreateTask;
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
    private ChoiceBox cbPriority;
    @FXML
    private ChoiceBox cbTaskList;
    @FXML
    private ChoiceBox cbSection;

    @FXML
    private DatePicker dpDueDate;

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblSortBy;
    @FXML
    private Label lblEmail;

    @FXML
    public ScrollPane spHomeAllTask;

    @FXML
    private TextArea txtBiography;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextArea txtComment;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtTitle;
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
    private void openRegisterUser() {
        View.primaryStage.hide();
        View.instance().register();
    }
    @FXML
    private void registerNewAccount() {
        View.instance().errorMsg("Not yet implemented: User create method must be finished.");
        // close register window and open primary window, after user creates an account (successfully)
        if (AccountContext.CURRENT_ACCOUNT != null &&
                (AccountContext.CURRENT_ACCOUNT instanceof UserAccount || AccountContext.CURRENT_ACCOUNT instanceof AdminAccount)
        ) {
            View.secondaryStage.close();
            View.primaryStage.show();
        }
    }

    @FXML
    private void cancelStage(ActionEvent event) {
        if (View.secondaryStage != null) View.secondaryStage.close();
        if (View.primaryStage != null) View.primaryStage.show();
    }

    @FXML
    private void cancelError(ActionEvent event) {
        if (View.errorStage != null) View.errorStage.close();
        if (View.secondaryStage != null) View.secondaryStage.show();
    }

    @FXML
    private void btnCreateList(ActionEvent event) throws IOException {
        View.instance().createList();

    }
    @FXML
    private void btnCreateSection(ActionEvent event) throws IOException {
        View.instance().createSection();
    }
    @FXML
    private void btnCreateTask(ActionEvent event) throws IOException {
        View.instance().createTask();
    }


    public void populateTaskListAccordion() throws IOException {
        UserAccount account = (UserAccount) AccountContext.CURRENT_ACCOUNT;
        Iterator<TaskList> iter;
        VBox DisplayVBox = new VBox();
        TaskList currentList;
        Section currentSection;
        Task currentTask;
        Task currentSubTask;
        if(account.getTaskLists().getSubTaskLists() != null) {
            iter =  account.getTaskLists().getSubTaskLists().iterator();


            while(iter.hasNext()) {
                currentList = iter.next();
                HBox TaskListHBox = new HBox();
                //TitledPane taskListPane = new TitledPane(currentList.getTitle(), SectionAccordion);
                Text currentListText = new Text(currentList.getTitle());
                TaskListHBox.getChildren().add(currentListText);
                Iterator<Section> iter2;
                DisplayVBox.getChildren().add(TaskListHBox);
                if(currentList.getSections() != null) {
                    iter2 = currentList.getSections().iterator();
                    while (iter2.hasNext()) {
                        currentSection = iter2.next();
                        HBox SectionHBox = new HBox();
                        //TitledPane taskSectionPane = new TitledPane(currentSection.getTitle(), taskVBox);
                        Text currentSectionText = new Text(currentSection.getTitle());
                        SectionHBox.getChildren().add(currentSectionText);
                        Iterator<Task> iter3;
                        DisplayVBox.getChildren().add(SectionHBox);
                        if(currentSection.getTasks() != null) {
                            iter3 = currentSection.getTasks().iterator();
                            while (iter3.hasNext()) {
                                currentTask = iter3.next();
                                HBox TaskHBox = new HBox();
                                Text currentTaskText = new Text(currentTask.getTitle());
                                TaskHBox.getChildren().add(currentTaskText);
                                Iterator<Task> iter4;
                                DisplayVBox.getChildren().add(TaskHBox);
                                if(currentTask.getSubtasks() != null) {
                                    iter4 = currentTask.getSubtasks().iterator();
                                    while (iter4.hasNext()) {
                                        currentSubTask = iter4.next();
                                        HBox subTaskHBox = new HBox();
                                        Text currentSubTaskText = new Text(currentSubTask.getTitle());
                                        subTaskHBox.getChildren().add(currentSubTaskText);
                                        DisplayVBox.getChildren().add(subTaskHBox);
                                    }
                                }
                                //DisplayVBox.getChildren().add(TaskHBox);
                            }
                        }


                        //taskSectionPane.setContent(taskVBox);
                        //SectionAccordion.getPanes().add(taskSectionPane);
                        //DisplayVBox.getChildren().add(SectionHBox);
                    }
                }

                //taskSectionPane.setContent(taskVBox);
                //SectionAccordion.getPanes().add(taskSectionPane);


                //DisplayVBox.getChildren().add(TaskListHBox);
            }
        }


        apHomeAllTask.getChildren().add(DisplayVBox);
        //spHomeAllTask.setContent(DisplayVBox);

    }

    public void handle(ActionEvent event) {
        switch (((Node)event.getTarget()).getId()) {
            case "btnRegisterUser":
                handle(Event.Register);
                break;
            case "btnLoginUser":
                handle(Event.Login);
                break;
            case "btnLoginRegister":
                ((SystemState) SystemState.instance()).setState(AccountCreateState.instance());
                openRegisterUser();
                break;
            case "btnCreateTask":
                try {
                    btnCreateTask(event);
                } catch (IOException ex) {
                    handle(Event.ViewTaskList);
                }
            case "btnFinishCreateTask":
                handle(Event.CreateTask);
                break;
            case "btnCreateList":
                try {
                    btnCreateList(event);
                } catch (IOException ex) {
                    handle(Event.ViewTaskList);
                }
            case "btnFinishCreateList":
                handle(Event.CreateTaskList);
                break;
            case "btnCreateSection":
                try {
                    btnCreateSection(event);
                } catch (IOException ex) {
                    handle(Event.ViewTaskList);
                }
            case "btnFinishCreateSection":
                handle(Event.CreateSection);
                break;/*
            case UPDATE_TASK:
                handle(Event.UpdateTask);
                break;
            case UPDATE_TASKLIST:
                handle(Event.UpdateTaskList);
                break;
            case UPDATE_SECTION:
                handle(Event.UpdateSection);
                break;
            case "btnEditAccount":
                try {
                    btnEditAccount();// not added yet
                } catch (IOException ex) {
                    handle(Event.ViewTaskList);
                }
                break;
            case "btnFinishEditAccount":
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
                break;*/
            case "btnLogout":
                handle(Event.Logout);
                break;/*
            case CLOSE_APP:
                handle(Event.CloseApp);
                break;
            */
            case "btnCancel":
                cancelStage(event);
                //handle(Event.Cancel);
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
     * Assigns the current user's tasklist to the TaskListInfoState
     *
     * @author Brandon Watkins
     */
    public void showAllTasks() {
        Account user = AccountContext.CURRENT_ACCOUNT;
        if (user == null || user instanceof NullAccount || user instanceof AdminAccount) return;
        ((TaskListInfoState) TaskListInfoState.instance()).setState(((UserAccount) user).getTaskLists());
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
     *********************************************************************************************** */


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
