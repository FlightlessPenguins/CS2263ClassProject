/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.ErrorState;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.model.state.section.SectionInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.util.Hashtable;
import java.util.List;

public class InfoCommand implements Command {
    /*
        If reading directly from the form's fields, replace all "necessaryClassFields.get("id")" with whatever to read from form.
     */

    Event event;
    Hashtable<String, Object> necessaryClassFields;

    public InfoCommand(Event event, Hashtable<String, Object> necessaryClassFields) {
        this.event = event;
        this.necessaryClassFields = necessaryClassFields;
    }

    /**
     * Executes the command.
     *
     * @author Brandon Watkins
     */
    @Override
    public void execute() {
        if (necessaryClassFields != null && !necessaryClassFields.isEmpty() && event != null) {
            /*
                Ensuring the ID is in Integer format
             */
            Integer id = null;
            if (necessaryClassFields.get("id") instanceof String) {
                String temp = (String)necessaryClassFields.remove("id");
                id = Integer.parseInt(temp);
            }
            else if (necessaryClassFields.get("id") instanceof Integer) {
                id = (Integer)necessaryClassFields.get("id");
            }
            else return;

            /*
                Ensuring the user is logged into a UserAccount if they're viewing a to do list
             */
            Account account = AccountContext.CURRENT_ACCOUNT;
            if (account == null) {
                State ErrorState = new ErrorState("Unable to locate account.");
                return;
            }
            UserAccount user = null;
            if (account instanceof UserAccount) user = (UserAccount)account;
            else if (event != Event.ViewUser) {
                State ErrorState = new ErrorState("Account cannot have a todo list.");
                return;
            }

            /*
                handle the event
             */
            switch (event) {
                case ViewUser:
                    ((AccountInfoState)(AccountInfoState.instance())).setState((((AccountListState)AccountListState.instance())).getAccount(id));
                    break;
                case ViewTaskList:
                    ((TaskListInfoState)TaskListInfoState.instance()).setState(user.getTaskLists().findTaskList(id));
                    break;
                case ViewSection:
                    ((SectionInfoState)SectionInfoState.instance()).setState(user.getTaskLists().findSection(id));
                    break;
                case ViewTask:
                    ((TaskInfoState)TaskInfoState.instance()).setState(user.getTaskLists().findTask(id));
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }

}
