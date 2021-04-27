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
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountCreateState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountInfoState;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountUpdateState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.sort.Order;
import edu.isu.cs.cs2263.todoListManager.sort.SortOrder;
import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.util.*;

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
    public void execute(Dictionary<String,String> args) {
        if (event != null) {
            switch (event) {
                case SortTasks:
                    sortTasks(args.get("sortOrder"));
                    break;
                case SearchTasks:
                    searchTasks(args.get("searchTerm"));
                    break;
                case FilterTasks:
                    List<String> filters = null;
                    if (args.get("filters") != null && args.get("filters") != "") {
                        String[] f = args.get("filters").split(",");
                        for (int i = 0; i < f.length; i++) {
                            f[i] = f[i].trim();
                        }
                        filters = Arrays.asList(f.clone());
                    }
                    filterTasks(filters);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }

    /**
     *
     * @param order
     *
     * @author Brandon Watkins
     */
    private void sortTasks(String order) {
        if (AccountContext.CURRENT_ACCOUNT instanceof UserAccount) {
            UserAccount user = (UserAccount) AccountContext.CURRENT_ACCOUNT;
            TaskList tl = ((TaskListInfoState) AccountInfoState.instance()).getState();
            if (tl == null) tl = user.getTaskLists();
            TaskListIterator iterator = (TaskListIterator) tl.iterator();
            List<Task> tasks = iterator.getTasks();
            Collections.sort(tasks, order == null ? null : order.equals("ascending") ? null : Collections.reverseOrder());
            TaskList tl2 = new TaskList(0);
            for (int i = 0; i < tasks.size(); i++) {
                tl2.addTask(tasks.get(i));
            }
            ((TaskListInfoState) TaskListInfoState.instance()).setState(tl2);
        }
        ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
    }

    /**
     *
     * @param searchTerm
     *
     * @author Brandon Watkins
     */
    private void searchTasks(String searchTerm) {
        Account user = AccountContext.CURRENT_ACCOUNT;
        if (user instanceof UserAccount) {
            TaskList tl = null;
            if (((TaskListInfoState) TaskListInfoState.instance()).getState() != null) tl = ((TaskListInfoState) AccountInfoState.instance()).getState().search(searchTerm);
            ((TaskListInfoState) TaskListInfoState.instance()).setState(tl);
            ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
        }
    }

    /**
     *
     * @param filters
     *
     * @author Brandon Watkins
     */
    private void filterTasks(List<String> filters) {
        Account user = AccountContext.CURRENT_ACCOUNT;
        if (user instanceof UserAccount) {
            TaskList tl = null;
            if (((TaskListInfoState) TaskListInfoState.instance()).getState() != null) tl = ((TaskListInfoState) AccountInfoState.instance()).getState();
            ((TaskListInfoState) TaskListInfoState.instance()).setState(tl);
            for (String s : filters) {
                tl = ((TaskListInfoState) TaskListInfoState.instance()).getState().search(s);
                ((TaskListInfoState) TaskListInfoState.instance()).setState(tl);
            }
        }
        ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
    }

}
