/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.account;

import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import jdk.jshell.spi.ExecutionControl;

public class AccountCreateState implements State {

    /**
     * Performs all necessary tasks before changing state.
     * <p>Make sure to call the context's changeState(this) by the end of run().
     *
     * @author Brandon Watkins
     */
    @Override
    public void run() {
        throw new RuntimeException("not implemented yet.");
    }

    /**
     * Gets the next available ID number.
     *
     * @return (int) The next available ID number.
     *
     * @author Brandon Watkins
     */
    private int getNextID() throws ExecutionControl.NotImplementedException {
        // Read in ID counter from file
        Read.readNextID("account");
        // increment counter
        // write new counter to file
        // return counter that was read from file.
        throw new ExecutionControl.NotImplementedException("getNextID not implemented yet.");
    }

    /**
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new AccountCreateState();
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
