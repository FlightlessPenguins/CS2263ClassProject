/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.taskList;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountLoginState;
import edu.isu.cs.cs2263.todoListManager.model.state.section.SectionInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskInfoState;

public class TaskListInfoState implements State {

    private TaskList state = null;

    public void setState(TaskList taskListState) {
        state = taskListState;
    }

    public TaskList getState() {
        if (state == null) {
            if (((AccountContext) AccountContext.instance()).getCurrentAccount() instanceof UserAccount)
                state = ((UserAccount) ((AccountContext) AccountContext.instance()).getCurrentAccount()).getTaskLists();
        }
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
            case "TaskListInfoState": // They clicked on "Home", or another tasklist
                ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
                break;
            case "TaskListUpdateState": // They clicked on edit tasklist
                ((SystemState) SystemState.instance()).setState(TaskListUpdateState.instance());
                break;
            case "TaskInfoState": // They clicked on a task in the tasklist
                ((SystemState) SystemState.instance()).setState(TaskInfoState.instance());
                break;
            case "SectionInfoState": // They clicked on a section in a tasklist
                ((SystemState) SystemState.instance()).setState(SectionInfoState.instance());
                break;
        }
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new TaskListInfoState();
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
