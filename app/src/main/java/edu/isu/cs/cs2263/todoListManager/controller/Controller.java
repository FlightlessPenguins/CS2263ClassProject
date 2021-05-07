package edu.isu.cs.cs2263.todoListManager.controller;

import edu.isu.cs.cs2263.todoListManager.controller.command.*;
import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.context.Context;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
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
import java.time.LocalDate;
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
    public ChoiceBox cbPriority;
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
    private Text lblUserName;

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


    /**
     * Small method that assists in populating the priority choice box in the Task Creation screen
     *
     * @author Liam Andrus
     */
    public void populatePrioCb() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Low", "Medium", "High", "Highest");
        //populate the Choicebox;
        cbPriority.setItems(list);
    }

    public void populateHomeScrollPane() throws IOException {
        UserAccount account = (UserAccount) AccountContext.CURRENT_ACCOUNT;
        Iterator<Task> iter;
        VBox DisplayVBox = new VBox();
        Task currentTask;
        if (account.getTaskLists().iterator().hasNext()) {
            iter = account.getTaskLists().iterator();
            while (iter.hasNext()) {
                currentTask = iter.next();
                HBox TaskHbox = new HBox();
                Text currentTaskText = new Text(currentTask.getTitle());
                TaskHbox.getChildren().add(currentTaskText);
                DisplayVBox.getChildren().add(TaskHbox);
            }
        }
        apHomeAllTask.getChildren().add(DisplayVBox);

        if (AccountContext.CURRENT_ACCOUNT != null) lblUserName.setText(
                AccountContext.CURRENT_ACCOUNT instanceof NullAccount ? "Profile" : AccountContext.CURRENT_ACCOUNT.getFirstName());
    }

    /**
     * Handles all button events
     *
     * @param event (ActionEvent) The event being handled.
     *
     * @author Brandon Watkins
     */
    public void handle(ActionEvent event) {
        String id = "";
        if (event == null) return;
        EventTarget tar = event.getTarget();
        if (tar == null) return;
        try {
            id = tar.toString();
            id = id.substring(id.indexOf("id=") + 3, id.length());
            id = id.substring(0, id.indexOf(","));
        } catch (Exception ex) {
            id = "";
        }

        //switch (event != null ? event.getTarget() != null ? ((Node)event.getTarget()).getId() : "" : "") {//throwing InvocationTargetException
        switch (id) {
            case "btnRegisterUser", "Register":
                handle(Event.Register);
                break;
            case "btnLoginUser", "Login":
                handle(Event.Login);
                break;
            case "btnLoginRegister", "New User":
                ((SystemState) SystemState.instance()).setState(AccountCreateState.instance());
                openRegisterUser();
                break;
            case "btnCreateTask", "New Task":
                try {
                    btnCreateTask(event);
                } catch (IOException ex) {
                    handle(Event.ViewTaskList);
                }
            case "btnFinishCreateTask", "Create Task":
                handle(Event.CreateTask);
                break;
            case "btnCreateList", "New List":
                try {
                    btnCreateList(event);
                } catch (IOException ex) {
                    handle(Event.ViewTaskList);
                }
            case "btnFinishCreateList", "Create List":
                handle(Event.CreateTaskList);
                break;
            case "btnCreateSection", "New Section":
                try {
                    btnCreateSection(event);
                } catch (IOException ex) {
                    handle(Event.ViewTaskList);
                }
            case "btnFinishCreateSection", "Create Section":
                handle(Event.CreateSection);
                break;
            case "btnFinishEditAccount", "Update Account":
                handle(Event.UpdateUser);
                break;
            case "btnCancel", "Cancel":
                cancelStage(event);
                //handle(Event.Cancel);
                break;
            case "btnLogout", "Logout":
                handle(Event.Logout);
                break;
            case "btnCloseApp", "Save and Exit":
                handle(Event.CloseApp);
                break;
            case "btnReload", "Reload":
                showAllTasks();
                break;
            case "btnSearch", "Search":
                handle(Event.SearchTasks);
                break;
            case "btnSort", "Sort":
                handle(Event.SortTasks);
                break;
            case "btnFilter", "Filter":
                handle(Event.FilterTasks);
                break;
            default:
                // do nothing
                break;
            /*
            case "btnEditAccount":
                try {
                    btnEditAccount();// not added yet
                } catch (IOException ex) {
                    handle(Event.ViewTaskList);
                }
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
            case VIEW_LIST_OF_ALL_USERS:
                handle(Event.ViewListOfAllUsers);
                break;
            */
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
                args.put("title", txtTitle != null ? txtTitle.getText() : null);
                args.put("description", txtDescription != null ? txtDescription.getText() : null);
                args.put("comment", txtComment != null ? txtComment.getText() : null);
                //args.put("parentTaskListID", txtTitle != null ? txtTitle.getParent().getId() : null);
                break;
            case UpdateTaskList:
                c = new UpdateCommand(event);
                //args.put("id", txtTitle.getParent().getId());
                args.put("title", txtTitle != null ? txtTitle.getText() : null);
                args.put("description", txtDescription != null ? txtDescription.getText() : null);
                args.put("comment", txtComment != null ? txtComment.getText() : null);
                args.put("isListArchived", cbIsListArchived != null ? cbIsListArchived.isSelected() ? "true" : "false" : "false");
                //args.put("parentTaskListID", txtTitle != null ? txtTitle.getParent().getParent().getId() : null);
                break;
            case ViewTaskList:
                c = new InfoCommand(event);
                //args.put("id", lblTitle != null ? lblTitle.getParent().getId() : null);
                break;
            case RescheduleTaskList:
                c = new UpdateCommand(event);
                String dateString = null;
                if (dpDueDate != null) {
                    LocalDate date = dpDueDate.getValue();
                    if (date != null) dateString = date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/" + date.getDayOfWeek().getValue();
                }
                args.put("dueDate", dateString); // may not work
                //args.put("id", lblTitle != null ? lblTitle.getParent().getId() : null);
                break;
            case ArchiveTaskList:
                c = new UpdateCommand(event);
                args.put("isListArchived", "true");
                //args.put("id", lblTitle != null ? lblTitle.getParent().getId() : null);
                break;
            case SortTasks:
                c = new ViewCommand(event);
                args.put("sortCategory", lblSortBy != null ? lblSortBy.getText() : null);
                args.put("sortOrder", cbSortDirection != null ? cbSortDirection.isSelected() ? "ascending" : "descending" : "ascending");
                break;
            case FilterTasks:
                c = new ViewCommand(event);
                args.put("filters", txtFilters != null ? txtFilters.getText() : null);
                break;
            case SearchTasks:
                c = new ViewCommand(event);
                args.put("searchTerm", txtSearch != null ? txtSearch.getText() : null);
                break;
            case CreateSection:
                c = new CreateCommand(event);
                args.put("title", txtTitle != null ? txtTitle.getText() : null);
                args.put("description", txtDescription != null ? txtDescription.getText() : null);
                args.put("defaultSection", "false");
                //args.put("parentTaskListID", txtTitle != null ? txtTitle.getParent().getId() : null);
                break;
            case UpdateSection:
                c = new UpdateCommand(event);
                //args.put("id", txtTitle != null ? txtTitle.getParent().getId() : null);
                args.put("title", txtTitle != null ? txtTitle.getText() : null);
                args.put("description", txtDescription != null ? txtDescription.getText() : null);
                break;
            case ViewSection:
                c = new InfoCommand(event);
                //args.put("id", lblTitle != null ? lblTitle.getParent().getId() : null);
                break;
            case CreateTask:
                c = new CreateCommand(event);
                args.put("title", txtTitle != null ? txtTitle.getText() : null);
                args.put("description", txtDescription != null ? txtDescription.getText() : null);
                args.put("labels", txtLabels != null ? txtLabels.getText() : null);
                String dateString2 = null;
                if (dpDueDate != null) {
                    LocalDate date = dpDueDate.getValue();
                    if (date != null) dateString2 = date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/" + date.getDayOfWeek().getValue();
                }
                args.put("dueDate", dateString2); // may not work
                //args.put("parentTaskID", PARENT_TASK_ID);
                //args.put("parentSectionID", lblTitle != null ? lblTitle.getParent().getId() : null);//would only work if creating from a section's view
                break;
            case UpdateTask:
                c = new UpdateCommand(event);
                //args.put("id", txtTitle != null ? txtTitle.getParent().getId() : null);
                args.put("title", txtTitle != null ? txtTitle.getText() : null);
                args.put("description", txtDescription != null ? txtDescription.getText() : null);
                args.put("labels", txtLabels != null ? txtLabels.getText() : null);
                String dateString3 = null;
                if (dpDueDate != null) {
                    LocalDate date = dpDueDate.getValue();
                    if (date != null) dateString3 = date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/" + date.getDayOfWeek().getValue();
                }
                args.put("dueDate", dateString3); // may not work
                //args.put("parentTaskID", PARENT_TASK_ID);
                //args.put("parentSectionID", PARENT_SECTION_ID);
                break;
            case ViewTask:
                c = new InfoCommand(event);
                //args.put("taskID", lblTitle != null ? lblTitle.getParent().getId() : null);
                break;
            case Register:
                c = new CreateCommand(event);
                args.put("email", txtEmail != null ? txtEmail.getText() : null);
                args.put("password", txtPassword != null ? txtPassword.getText() : null);
                args.put("confirmPassword", txtPasswordConfirm != null ? txtPasswordConfirm.getText() : null);
                args.put("firstName", txtFirstName != null ? txtFirstName.getText() : null);
                args.put("lastName", txtLastName != null ? txtLastName.getText() : null);
                args.put("biography", txtBiography != null ? txtBiography.getText() : null);
                break;
            case UpdateUser:
                c = new UpdateCommand(event);
                //args.put("id", lblEmail != null ? lblEmail.getParent().getId() : null);
                args.put("email", txtEmail != null ? txtEmail.getText() : null);
                args.put("password", txtPassword != null ? txtPassword.getText() : null);
                args.put("confirmPassword", txtPasswordConfirm != null ? txtPasswordConfirm.getText() : null);
                args.put("firstName", txtFirstName != null ? txtFirstName.getText() : null);
                args.put("lastName", txtLastName != null ? txtLastName.getText() : null);
                args.put("biography", txtBiography != null ? txtBiography.getText() : null);
                break;
            case ViewUser:
                c = new InfoCommand(event);
                args.put("email", txtEmail != null ? txtEmail.getText() : null);
                break;
            case ViewListOfAllUsers:
                c = new ListCommand(event);
                break;
            case Login:
                c = new SystemCommand(event);
                args.put("email", txtEmail != null ? txtEmail.getText() : null);
                args.put("password", txtPassword != null ? txtPassword.getText() : null);
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

}
