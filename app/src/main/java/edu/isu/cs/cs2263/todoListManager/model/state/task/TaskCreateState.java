/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.task;

import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountLoginState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;

public class TaskCreateState implements State {

    private Task state = null;

    public void setState(Task state) {
        this.state = state;
    }

    public Task getState() {
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
            case "AccountLoginState": // They logged out
                ((SystemState) SystemState.instance()).setState(AccountLoginState.instance());
                break;
            case "TaskListInfoState": // They clicked on "Home", or successfully created a task.
                ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
                break;
            // Should be taking them to the tasklist, not the individual task
            //case "TaskInfoState": // They successfully created a task, taking them to their task
        }
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new TaskCreateState();
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
