package edu.isu.cs.cs2263.todoListManager.model.state.account;

import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;

public class AccountLoginState implements State {

    @Override
    public void setNextState(State state, Object args) {
        switch(state.getClass().getSimpleName()) {
            case "AccountLoginState": // They entered invalid login credentials. retry
                ((SystemState) SystemState.instance()).setState(AccountLoginState.instance());
                break;
            case "TaskListInfoState": // They logged in successfully
                ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
                break;
        }
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new AccountLoginState();
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
