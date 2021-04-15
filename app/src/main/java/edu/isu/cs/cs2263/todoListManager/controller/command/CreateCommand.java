/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.util.Hashtable;

public class CreateCommand implements Command {

    Event event;
    Hashtable<String, Object> necessaryClassFields;

    public CreateCommand(Event event, Hashtable<String, Object> necessaryClassFields) {
        this.event = event;
        this.necessaryClassFields = necessaryClassFields;
    }

    /**
     * Executes the command.
     *
     * @author Brandon Watkins
     */
    @Override
    public void execute() {
        if (necessaryClassFields != null && event != null) {
            switch (event) {
                case Register:

                    break;
                case CreateTaskList:

                    break;
                case CreateSection:

                    break;
                case CreateTask:

                    break;
                default:
                    // do nothing
                    break;
            }
        }
        throw new RuntimeException("not implemented yet.");
    }

}
