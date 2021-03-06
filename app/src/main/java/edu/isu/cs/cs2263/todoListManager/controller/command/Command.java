/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.controller.command;

import java.util.Dictionary;

public interface Command {

    /**
     * Executes the command.
     *
     * @author Brandon Watkins
     */
    public void execute(Dictionary<String,String> args);

}
