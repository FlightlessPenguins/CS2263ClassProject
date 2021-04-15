/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import edu.isu.cs.cs2263.todoListManager.view.Event;

import java.util.Hashtable;

public class SystemCommand implements Command {

    Event event;
    Hashtable<String, Object> necessaryClassFields;

    public SystemCommand(Event event, Hashtable<String, Object> necessaryClassFields) {
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
        if (event != null) {
            switch (event) {
                case Cancel:

                    break;
                case CloseApp:

                    break;
                case OpenApp:

                    break;
                case Login:

                    break;
                case Logout:

                    break;
                default:
                    // do nothing
                    break;
            }
        }
        throw new RuntimeException("not implemented yet.");
    }

}
