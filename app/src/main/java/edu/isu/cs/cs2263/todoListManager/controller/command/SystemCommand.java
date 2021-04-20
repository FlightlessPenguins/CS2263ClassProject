/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.controller.Controller;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.NullAccount;
import edu.isu.cs.cs2263.todoListManager.model.state.ErrorState;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.view.Event;

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
    public void execute() {
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
                    ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.Login);
                    break;
                case Login:
                    if (!(Controller.instance().loginEmailTxt == null || Controller.instance().loginEmailTxt.getText() == null || Controller.instance().loginEmailTxt.getText().length() < 1 ||
                            Controller.instance().loginPasswordTxt == null || Controller.instance().loginPasswordTxt.getText() == null || Controller.instance().loginPasswordTxt.getText().length() < 1)) {
                        Account user = Controller.login(Controller.instance().loginEmailTxt.getText(), Controller.instance().loginPasswordTxt.getText());
                        if (!(user == null || user instanceof NullAccount)) ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.Home);
                        else ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.Login);
                    }
                    else {
                        ErrorState error = new ErrorState("Please enter an email and password.");
                    }
                    break;
                case Logout:
                    Controller.logout();
                    ((SystemState) SystemState.instance()).setState(SystemState.SystemStateEnum.Login);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }



}
