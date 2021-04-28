/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.state.ErrorState;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountLoginState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.util.Dictionary;
import java.util.Hashtable;

public class ListCommand implements Command {

    Event event;

    public ListCommand(Event event) {
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
            /*
                Ensuring the user is logged into an AdminAccount if they're viewing a user list
             */
            Account account = AccountContext.CURRENT_ACCOUNT;
            if (account == null) {
                State ErrorState = new ErrorState("You don't appear to be logged in.");
                SystemState.instance().setNextState(AccountLoginState.instance(), null);
                return;
            }
            if (!(account instanceof AdminAccount) && event == Event.ViewListOfAllUsers) {
                State ErrorState = new ErrorState("You must have admin privileges to retrieve a list of users.");
                return;
            }

            switch (event) {
                case ViewListOfAllUsers:
                    ((AccountListState) AccountListState.instance()).setState(Read.readAllUserData());
                    SystemState.instance().setNextState(AccountListState.instance(), null);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }
}
