/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.context;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.*;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskListContext implements Context {

    private TaskList currentTaskList = ((AccountContext)(AccountContext.instance())).getCurrentAccount() instanceof UserAccount ?
            ((UserAccount)(((AccountContext)(AccountContext.instance())).getCurrentAccount())).getTaskLists() : new TaskList(0, null, null, null, null);

    private final List<State> states = new ArrayList<State>(
            Arrays.asList(
                    TaskListUpdateState.instance(),
                    TaskListInfoState.instance(),
                    TaskListCreateState.instance()
            )
    );

    /**
     * Changes the state from the current state to the state following the current state's transition.
     *
     * @param nextState The state that just finished its 'run()'.
     *
     * @author Brandon Watkins
     */
    @Override
    public void changeState(State nextState) {
        throw new RuntimeException("not implemented yet.");
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public Object instance() {
        throw new RuntimeException("not implemented yet.");
    }

    /**
     * Converts a List of TaskLists to a single TaskList containing SubTaskLists
     *
     * @param lists (List<TaskList>) List of TaskLists.
     * @return (TaskList) Parent TaskList containing all given TaskLists as children.
     *
     * @author Brandon Watkins
     */
    private TaskList convertListsToList(List<TaskList> lists) throws ExecutionControl.NotImplementedException {
        TaskList output = new TaskList(0, null, null, null, null);
        for (TaskList list : lists) {
            output.addSubTaskList(list);
        }
        return output;
    }

}
