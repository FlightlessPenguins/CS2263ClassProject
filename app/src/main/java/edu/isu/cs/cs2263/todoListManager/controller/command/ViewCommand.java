/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.task.Task;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskList;
import edu.isu.cs.cs2263.todoListManager.model.objects.taskList.TaskListIterator;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountUpdateState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.sort.Order;
import edu.isu.cs.cs2263.todoListManager.sort.SortOrder;
import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class ViewCommand implements Command {

    Event event;

    public ViewCommand(Event event) {
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
                case SortTasks:
                    //sortTasks(SORTORDER);
                    break;
                case SearchTasks:
                    //searchTasks(SEARCHTERM);
                    break;
                case FilterTasks:
                    //filterTasks(FILTERS);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }

    private void sortTasks(SortOrder order) {
        if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
            UserAccount user = (UserAccount) AccountContext.CURRENT_ACCOUNT;
            TaskList tl = ((TaskListInfoState) AccountInfoState.instance()).getState();
            if (tl == null) tl = user.getTaskLists();
            TaskListIterator iterator = (TaskListIterator) tl.iterator();
            List<Task> tasks = iterator.getTasks();
            Collections.sort(tasks, order.getOrder() == Order.Ascending ? null : Collections.reverseOrder());
            TaskList tl2 = new TaskList(0);
            for (int i = 0; i < tasks.size(); i++) {
                tl2.addTask(tasks.get(i));
            }
            ((TaskListInfoState) AccountInfoState.instance()).setState(tl2);
        }
    }

    private void searchTasks(String searchTerm) {
        Account user = AccountContext.CURRENT_ACCOUNT;
        if (user instanceof UserAccount) {
            TaskList tl = null;
            if (((TaskListInfoState) AccountInfoState.instance()).getState() != null) tl = ((TaskListInfoState) AccountInfoState.instance()).getState().search(searchTerm);
            ((TaskListInfoState) AccountInfoState.instance()).setState(tl);
        }
    }

    private void filterTasks(List<String> filters) {
        Account user = AccountContext.CURRENT_ACCOUNT;
        if (user instanceof UserAccount) {
            TaskList tl = null;
            if (((TaskListInfoState) AccountInfoState.instance()).getState() != null) tl = ((TaskListInfoState) AccountInfoState.instance()).getState();
            ((TaskListInfoState) AccountInfoState.instance()).setState(tl);
            for (String s : filters) {
                tl = ((TaskListInfoState) AccountInfoState.instance()).getState().search(s);
                ((TaskListInfoState) AccountInfoState.instance()).setState(tl);
            }
        }
    }

}
