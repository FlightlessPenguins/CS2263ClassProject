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
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.model.state.section.SectionInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class InfoCommand implements Command {

    Event event;

    public InfoCommand(Event event) {
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
            //Ensuring the user is logged into a UserAccount if they're viewing a to do list
            Account account = AccountContext.CURRENT_ACCOUNT;
            if (account == null) {
                State ErrorState = new ErrorState("Unable to locate account.");
                ((SystemState) SystemState.instance()).setState(AccountCreateState.instance());
                return;
            }
            UserAccount user = null;
            if (account instanceof UserAccount) user = (UserAccount)account;
            else if (event != Event.ViewUser) {
                State ErrorState = new ErrorState("Account cannot have a to do list.");
                return;
            }

            String idString = args.get("id");
            int id = Integer.parseInt(idString);

            //handle the event
            switch (event) {
                case ViewUser:
                    ((AccountInfoState)(AccountInfoState.instance())).setState((((AccountListState)AccountListState.instance())).getAccount(id));
                    ((SystemState) SystemState.instance()).setState(AccountInfoState.instance());
                    break;
                case ViewTaskList:
                    ((TaskListInfoState)TaskListInfoState.instance()).setState(user.getTaskLists().findTaskList(id));
                    ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
                    break;
                case ViewSection:
                    ((SectionInfoState)SectionInfoState.instance()).setState(user.getTaskLists().findSection(id));
                    ((SystemState) SystemState.instance()).setState(SectionInfoState.instance());
                    break;
                case ViewTask:
                    ((TaskInfoState)TaskInfoState.instance()).setState(user.getTaskLists().findTask(id));
                    ((SystemState) SystemState.instance()).setState(TaskInfoState.instance());
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }

}
