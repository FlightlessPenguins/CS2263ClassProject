/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.view.Event;

public class InfoCommand implements Command {

    Event event;
    String[] necessaryClassFields;

    public InfoCommand(Event event, String[] necessaryClassFields) {
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
                case ViewUser:

                    break;
                case ViewTaskList:

                    break;
                case ViewSection:

                    break;
                case ViewTask:

                    break;
                default:
                    // do nothing
                    break;
            }
        }
        throw new RuntimeException("not implemented yet.");
    }

}
