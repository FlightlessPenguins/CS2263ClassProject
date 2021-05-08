/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.account;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;

public class AccountCreateState implements State {

    private Account state;

    public void setState(Account state) {
        this.state = state;
    }

    public Account getState() {
        return state;
    }

    /**
     * Performs all necessary tasks before changing state.
     * <p>Make sure to call the context's changeState(this) by the end of run().
     *
     * @author Brandon Watkins
     * @param state
     * @param args
     */
    @Override
    public void setNextState(State state, Object args) {
        switch(state.getClass().getSimpleName()) {
            case "TaskListInfoState": // account was created successfully, moving to their tasklist
                ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
                break;
            case "AccountLoginState": // they clicked cancel or back
                ((SystemState) SystemState.instance()).setState(AccountLoginState.instance());
                break;
            case "AccountCreateState": // registration failed for some reason. retry.
                ((SystemState) SystemState.instance()).setState(AccountCreateState.instance());
                break;
        }
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new AccountCreateState();
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static State instance() {
        return Helper.INSTANCE;
    }

}
