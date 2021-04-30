/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.controller.Controller;
import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.NullAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.state.ErrorState;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountLoginState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.util.Dictionary;
import java.util.Hashtable;

public class SystemCommand implements Command {

    Event event;

    public SystemCommand(Event event) {
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
            switch (event) {
                case Cancel:
                    // Take user back to main screen, using TaskListInfoState.
                    Account a = AccountContext.CURRENT_ACCOUNT;
                    if (a != null) {
                        if (a instanceof UserAccount) {
                            SystemState.instance().setNextState(TaskListInfoState.instance(), null);
                        }
                        else if (a instanceof AdminAccount) {
                            SystemState.instance().setNextState(AccountInfoState.instance(), null);
                        }
                        else {
                            SystemState.instance().setNextState(AccountLoginState.instance(), null);
                        }
                    }
                    else SystemState.instance().setNextState(AccountLoginState.instance(), null);
                    break;
                case CloseApp:
                    Controller.close();
                    break;
                case OpenApp:
                    SystemState.instance().setNextState(AccountLoginState.instance(), null);
                    break;
                case Login:
                    if (!(args.get("email") == null || args.get("email").length() < 1 ||
                            args.get("password") == null || args.get("password").length() < 1)) {
                        Account user = Controller.login(args.get("email"), args.get("password"));
                        if (!(user == null || user instanceof NullAccount))
                            if (user instanceof AdminAccount) {
                                SystemState.instance().setNextState(AccountListState.instance(), null);
                            }
                            else {
                                SystemState.instance().setNextState(TaskListInfoState.instance(), null);
                            }
                        else {
                            ErrorState error = new ErrorState("Invalid credentials.");
                            SystemState.instance().setNextState(AccountLoginState.instance(), null);
                        }
                    }
                    else {
                        ErrorState error = new ErrorState("Please enter an email and password.");
                    }
                    break;
                case Logout:
                    AccountContext.CURRENT_ACCOUNT = NullAccount.instance();
                    ((AccountContext)AccountContext.instance()).setCurrentAccount(NullAccount.instance());
                    ((AccountInfoState) AccountInfoState.instance()).setState(NullAccount.instance());
                    SystemState.instance().setNextState(AccountLoginState.instance(), null);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }



}
