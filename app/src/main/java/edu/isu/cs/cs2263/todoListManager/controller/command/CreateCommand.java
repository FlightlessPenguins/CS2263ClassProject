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

import java.util.Hashtable;

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

            /*switch (event) {
                case Register:
                    if (emailAddressInUse(EMAIL)) {
                        State ErrorState = new ErrorState("Email address is already in use.");
                        return;
                    }
                    UserAccount user = null;
                    if (EMAIL != null && EMAIL.length() > 1 &&
                            PASSWORD != null && PASSWORD.length() > 1 &&
                            FIRSTNAME != null && FIRSTNAME.length() > 0 &&
                            LASTNAME != null && LASTNAME.length() > 0) {
                        user = new UserAccount(BIOGRAPHY, null, EMAIL, PASSWORD, FIRSTNAME, LASTNAME);
                        Write.writeAccountData(user);
                        ((AccountListState) AccountListState.instance()).addAccount(user);
                        AccountContext.CURRENT_ACCOUNT = user;
                        ((AccountInfoState) AccountInfoState.instance()).setState(user);
                        ((AccountCreateState) AccountCreateState.instance()).setState(user);
                    }
                    else {
                        State ErrorState = new ErrorState("Missing required fields");
                    }
                    break;
                case CreateTaskList:
                if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
                TaskList tasklist = new TaskList(TITLE, DESCRIPTION, COMMENT);
                    UserAccount user2 = (UserAccount)AccountContext.CURRENT_ACCOUNT;
                    user2.getTaskLists().addSubTaskList(tasklist);
                    ((TaskListInfoState) TaskListInfoState.instance()).setState(tasklist);
                    ((TaskListCreateState) TaskListCreateState.instance()).setState(tasklist);
                }
                    break;
                case CreateSection:
                    if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
                        Section section = new Section(TITLE, DESCRIPTION, null, false);
                        UserAccount user3 = (UserAccount)AccountContext.CURRENT_ACCOUNT;
                        user3.getTaskLists().addSection(section);
                        ((SectionInfoState) SectionInfoState.instance()).setState(section);
                        ((SectionCreateState) SectionCreateState.instance()).setState(section);
                    }
                    break;
                case CreateTask:
                    if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
                        Task task = new Task(TITLE, DESCRIPTION, LABELS, DUEDATE, null, null);
                        UserAccount user4 = (UserAccount)AccountContext.CURRENT_ACCOUNT;
                        user4.getTaskLists().addTask(task);
                        ((TaskInfoState) TaskInfoState.instance()).setState(task);
                        ((TaskCreateState) TaskCreateState.instance()).setState(task);
                    }
                    break;
                default:
                    // do nothing
                    break;
            }*/
        }
    }

    private Boolean emailAddressInUse(String email) {
        for (Account account : ((AccountListState) AccountListState.instance()).getAccountsBackdoor()) {
            if (account.getEmail().equals(email)) return true;
        }
        return false;
    }

}
