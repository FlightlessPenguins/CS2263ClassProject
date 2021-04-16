/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.ErrorState;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountUpdateState;
import edu.isu.cs.cs2263.todoListManager.model.state.section.SectionCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.section.SectionInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.section.SectionUpdateState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskUpdateState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListUpdateState;
import edu.isu.cs.cs2263.todoListManager.storage.Write;
import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class UpdateCommand implements Command {

    Event event;

    public UpdateCommand(Event event) {
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
            switch (event) {
                case UpdateUser:
                    //updateAccount(ACCOUNTID, EMAIL, PASSWORD, FIRSTNAME, LASTNAME, BIOGRAPHY);
                    break;
                case UpdateTaskList:
                    //updateTaskList(TASKLISTID, TITLE, DESCRIPTION, COMMENT, ISARCHIVED);
                    break;
                case UpdateSection:
                    //updateSection(SECTIONID, TITLE, DESCRIPTION);
                    break;
                case UpdateTask:
                    //updateTask(TASKID, TITLE, DESCRIPTION, LABELS, DUEDATE, DATECOMPLETED);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }

    private void updateAccount(int id, String email, String password, String firstName, String lastName, String biography) {
        Account user = (((AccountListState)AccountListState.instance())).getAccount(id);
        if (user == null ) return;
        if (email != null && !user.getEmail().equals(email) && emailAddressInUse(email)) {
            State ErrorState = new ErrorState("Email address is already in use.");
            return;
        }
        if (!(email == null || email.length() < 1)) user.setEmail(email);
        if (!(password == null || password.length() < 1)) user.setPassword(((AccountContext)AccountContext.instance()).generateHash(password));
        if (!(firstName == null || firstName.length() < 1)) user.setFirstName(firstName);
        if (!(lastName == null || lastName.length() < 1)) user.setLastName(lastName);
        if (!(biography == null || biography.length() < 1) && user instanceof UserAccount) ((UserAccount) user).setBiography(biography);
        Write.writeAccountData(user);
        ((AccountInfoState) AccountInfoState.instance()).setState(user);
        ((AccountUpdateState) AccountCreateState.instance()).setState(user);
    }

    private Boolean emailAddressInUse(String email) {
        for (Account account : ((AccountListState) AccountListState.instance()).getAccountsBackdoor()) {
            if (account.getEmail().equals(email)) return true;
        }
        return false;
    }

    private void updateTaskList(int id, String title, String description, String comment, boolean isArchived) {
        TaskList tl = AccountContext.CURRENT_ACCOUNT instanceof UserAccount ? ((UserAccount) AccountContext.CURRENT_ACCOUNT).getTaskLists().findTaskList(id) : null;
        if (tl == null) return;
        tl.setTitle(title);
        tl.setDescription(description);
        tl.setComment(comment);
        tl.setArchived(isArchived);
        Write.writeAccountData(AccountContext.CURRENT_ACCOUNT);
        ((TaskListUpdateState) TaskListUpdateState.instance()).setState(tl);
        ((TaskListInfoState) TaskListInfoState.instance()).setState(tl);
    }

    private void updateSection(int id, String title, String description) {
        Section s = AccountContext.CURRENT_ACCOUNT instanceof UserAccount ? ((UserAccount) AccountContext.CURRENT_ACCOUNT).getTaskLists().findSection(id) : null;
        if (s == null) return;
        s.setTitle(title);
        s.setDescription(description);
        Write.writeAccountData(AccountContext.CURRENT_ACCOUNT);
        ((SectionUpdateState) SectionUpdateState.instance()).setState(s);
        ((SectionInfoState) SectionInfoState.instance()).setState(s);
    }

    private void updateTask(int id, String title, String description, List<String> labels, String dueDate, String completionDate) {
        Task t = AccountContext.CURRENT_ACCOUNT instanceof UserAccount ? ((UserAccount) AccountContext.CURRENT_ACCOUNT).getTaskLists().findTask(id) : null;
        if (t == null) return;
        if (title != null && title.length() > 0) t.setTitle(title);
        t.setDescription(description);
        t.setDescription(description);
        t.setLabels(labels);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date date;
            Calendar cal;
            cal = Calendar.getInstance();
            if (dueDate != null) {
                date = sdf.parse(dueDate);
                cal.getInstance().setTime(date);
                t.setDueDate(cal);
            }
            if (completionDate != null) {
                date = sdf.parse(completionDate);
                cal.getInstance().setTime(date);
                t.setDateCompleted(cal);
            }
        } catch (Exception ex) {
            // do nothing
        }
        Write.writeAccountData(AccountContext.CURRENT_ACCOUNT);
        ((TaskInfoState) TaskInfoState.instance()).setState(t);
        ((TaskUpdateState) TaskUpdateState.instance()).setState(t);
    }

    private void updateTask(int id, String title, String description, List<String> labels, Calendar dueDate, Calendar completionDate) {
        Task t = AccountContext.CURRENT_ACCOUNT instanceof UserAccount ? ((UserAccount) AccountContext.CURRENT_ACCOUNT).getTaskLists().findTask(id) : null;
        if (t == null) return;
        if (title != null && title.length() > 0) t.setTitle(title);
        t.setDescription(description);
        t.setDescription(description);
        t.setLabels(labels);
        t.setDueDate(dueDate);
        t.setDateCompleted(completionDate);
        Write.writeAccountData(AccountContext.CURRENT_ACCOUNT);
        ((TaskInfoState) TaskInfoState.instance()).setState(t);
        ((TaskUpdateState) TaskUpdateState.instance()).setState(t);
    }

}
