/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.controller.Controller;
import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.NullAccount;
import edu.isu.cs.cs2263.todoListManager.model.state.ErrorState;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
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
                    ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.Home);
                    break;
                case CloseApp:
                    Controller.close();
                    break;
                case OpenApp:
                    ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.LoginForm);
                    break;
                case Login:
                    if (!(args.get("email") == null || args.get("email").length() < 1 ||
                            args.get("password") == null || args.get("password").length() < 1)) {
                        Account user = Controller.login(args.get("email"), args.get("password"));
                        if (!(user == null || user instanceof NullAccount)) ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.Home);
                        else ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.LoginForm);
                    }
                    else {
                        ErrorState error = new ErrorState("Please enter an email and password.");
                    }
                    break;
                case Logout:
                    AccountContext.CURRENT_ACCOUNT = NullAccount.instance();
                    ((AccountContext)AccountContext.instance()).setCurrentAccount(NullAccount.instance());
                    ((AccountInfoState) AccountInfoState.instance()).setState(NullAccount.instance());
                    ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.LoginForm);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }



}
