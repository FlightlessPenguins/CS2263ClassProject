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
import java.util.*;

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
    public void execute(Dictionary<String,String> args) {
        if (event != null) {
            String idString = args.get("id");
            int id = Integer.parseInt(idString);
            if (id < 0) {
                ErrorState error = new ErrorState("No results found for given item.");
            }
            else {
                switch (event) {
                    case UpdateUser:
                        Account act = ((AccountListState) AccountListState.instance()).getAccount(id);
                        updateAccount(id,
                                args.get("email") != null && args.get("email").length() > 1 ? args.get("email") : act.getEmail(),
                                args.get("password") != null && args.get("password").length() > 1 ? args.get("password") : act.getPassword(),
                                args.get("firstName") != null && args.get("firstName").length() > 1 ? args.get("firstName") : act.getFirstName(),
                                args.get("lastName") != null && args.get("lastName").length() > 1 ? args.get("lastName") : act.getLastName(),
                                args.get("biography"));
                        break;
                    case UpdateTaskList:
                        updateTaskList(id, args.get("title"), args.get("description"), args.get("comment"), args.get("email") == "true" ? true : false);
                        break;
                    case UpdateSection:
                        updateSection(id, args.get("title"), args.get("description"));
                        break;
                    case UpdateTask:
                        List<String> labels = null;
                        if (args.get("labels") != null && args.get("labels") != "") {
                            String[] l = args.get("labels").split(",");
                            for (int i = 0; i < l.length; i++) {
                                l[i] = l[i].trim();
                            }
                            labels = Arrays.asList(l.clone());
                        }
                        updateTask(id, args.get("title"), args.get("description"), labels, args.get("dueDate"), args.get("dateCompleted"));
                        break;
                    default:
                        // do nothing
                        break;
                }
            }
        }
    }

    /**
     *
     * @param id
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @param biography
     *
     * @author Brandon Watkins
     */
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

    /**
     *
     * @param email
     * @return
     *
     * @author Brandon Watkins
     */
    private Boolean emailAddressInUse(String email) {
        for (Account account : ((AccountListState) AccountListState.instance()).getAccountsBackdoor()) {
            if (account.getEmail().equals(email)) return true;
        }
        return false;
    }

    /**
     *
     * @param id
     * @param title
     * @param description
     * @param comment
     * @param isArchived
     *
     * @author Brandon Watkins
     */
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

    /**
     *
     * @param id
     * @param title
     * @param description
     *
     * @author Brandon Watkins
     */
    private void updateSection(int id, String title, String description) {
        Section s = AccountContext.CURRENT_ACCOUNT instanceof UserAccount ? ((UserAccount) AccountContext.CURRENT_ACCOUNT).getTaskLists().findSection(id) : null;
        if (s == null) return;
        s.setTitle(title);
        s.setDescription(description);
        Write.writeAccountData(AccountContext.CURRENT_ACCOUNT);
        ((SectionUpdateState) SectionUpdateState.instance()).setState(s);
        ((SectionInfoState) SectionInfoState.instance()).setState(s);
    }

    /**
     *
     * @param id
     * @param title
     * @param description
     * @param labels
     * @param dueDate
     * @param completionDate
     *
     * @author Brandon Watkins
     */
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

    /**
     *
     * @param id
     * @param title
     * @param description
     * @param labels
     * @param dueDate
     * @param completionDate
     *
     * @author Brandon Watkins
     */
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
