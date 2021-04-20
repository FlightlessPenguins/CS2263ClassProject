/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.NullAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.ErrorState;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.model.state.section.SectionCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.section.SectionInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.storage.Write;
import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class CreateCommand implements Command {

    Event event;

    public CreateCommand(Event event) {
        this.event = event;
    }

    /**
     * Executes the command.
     *
     * @author Brandon Watkins
     */
    @Override
    public void execute() {
        if (event != null) {
            /*
                Ensuring the user is not logged in
             */
            Account account = AccountContext.CURRENT_ACCOUNT;
            if (account != null && !(account instanceof NullAccount)) {
                State ErrorState = new ErrorState("You must log out first.");
                return;
            }

            switch (event) {
                case Register:
                    //register(EMAIL, PASSWORD, FIRSTNAME, LASTNAME, BIOGRAPHY);
                    break;
                case CreateTaskList:
                    //createTaskList(TITLE, DESCRIPTION, COMMENT);
                    break;
                case CreateSection:
                    //createSection(TITLE, DESCRIPTION);
                    break;
                case CreateTask:
                    //createTask(TITLE, DESCRIPTION, LABELS, DUEDATE);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }

    private void register(String email, String password, String firstName, String lastName, String biography) {
        if (emailAddressInUse(email)) {
            State ErrorState = new ErrorState("Email address is already in use.");
            return;
        }
        UserAccount user = null;
        if (email != null && email.length() > 1 &&
                password != null && password.length() > 1 &&
                firstName != null && firstName.length() > 0 &&
                lastName != null && lastName.length() > 0) {
            user = new UserAccount(biography != null ? biography : null, null, email, password, firstName, lastName);
            Write.writeAccountData(user);
            ((AccountListState) AccountListState.instance()).addAccount(user);
            AccountContext.CURRENT_ACCOUNT = user;
            ((AccountInfoState) AccountInfoState.instance()).setState(user);
            ((AccountCreateState) AccountCreateState.instance()).setState(user);
        }
        else {
            State ErrorState = new ErrorState("Missing required fields");
        }
    }

    private Boolean emailAddressInUse(String email) {
        for (Account account : ((AccountListState) AccountListState.instance()).getAccountsBackdoor()) {
            if (account.getEmail().equals(email)) return true;
        }
        return false;
    }

    private void createTaskList(String title, String description, String comment) {
        if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
            TaskList tasklist = new TaskList(title, description, comment);
            UserAccount user2 = (UserAccount)AccountContext.CURRENT_ACCOUNT;
            user2.getTaskLists().addSubTaskList(tasklist);
            ((TaskListInfoState) TaskListInfoState.instance()).setState(tasklist);
            ((TaskListCreateState) TaskListCreateState.instance()).setState(tasklist);
        }
    }

    private void createSection(String title, String description) {
        if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
            Section section = new Section(title, description, null, false);
            UserAccount user3 = (UserAccount)AccountContext.CURRENT_ACCOUNT;
            user3.getTaskLists().addSection(section);
            ((SectionInfoState) SectionInfoState.instance()).setState(section);
            ((SectionCreateState) SectionCreateState.instance()).setState(section);
        }
    }

    private void createTask(String title, String description, List<String> labels, String dueDate) {
        if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = sdf.parse(dueDate);
                Calendar cal = Calendar.getInstance();
                cal.getInstance().setTime(date);
                Task task = new Task(title, description, labels, cal, null, null);
                UserAccount user4 = (UserAccount)AccountContext.CURRENT_ACCOUNT;
                user4.getTaskLists().addTask(task);
                ((TaskInfoState) TaskInfoState.instance()).setState(task);
                ((TaskCreateState) TaskCreateState.instance()).setState(task);
            } catch (Exception ex) {
                ((TaskInfoState) TaskInfoState.instance()).setState(null);
                ((TaskCreateState) TaskCreateState.instance()).setState(null);
                return;
            }
        }
    }

    private void createTask(String title, String description, List<String> labels, Calendar dueDate) {
        if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
            Task task = new Task(title, description, labels, dueDate, null, null);
            UserAccount user4 = (UserAccount)AccountContext.CURRENT_ACCOUNT;
            user4.getTaskLists().addTask(task);
            ((TaskInfoState) TaskInfoState.instance()).setState(task);
            ((TaskCreateState) TaskCreateState.instance()).setState(task);
        }
    }

}
