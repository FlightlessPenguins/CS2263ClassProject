/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.section;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.section.Section;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountLoginState;
import edu.isu.cs.cs2263.todoListManager.model.state.task.TaskInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;

public class SectionInfoState implements State {

    Section state;

    public void setState(Section state) {
        this.state = state;
    }

    public Section getState() {
        if (state == null && ((AccountContext) AccountContext.instance()).getCurrentAccount() instanceof UserAccount)
            state = ((UserAccount) ((AccountContext) AccountContext.instance()).getCurrentAccount()).getTaskLists().getSections().get(0);
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
            case "TaskListInfoState": // They clicked on "Home"
                ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
                break;
            case "SectionUpdateState": // They clicked on edit section
                ((SystemState) SystemState.instance()).setState(SectionUpdateState.instance());
                break;
            case "TaskInfoState": // They clicked on a task in the section
                ((SystemState) SystemState.instance()).setState(TaskInfoState.instance());
                break;
        }
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new SectionInfoState();
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
