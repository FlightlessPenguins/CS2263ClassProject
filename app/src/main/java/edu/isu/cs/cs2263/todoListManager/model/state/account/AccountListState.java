/**
 * @author Brandon Watkins, ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.account;

import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import jdk.jshell.spi.ExecutionControl;

import java.util.Iterator;
import java.util.List;

public class AccountListState implements State {
    //Instance Variables
    List<UserAccount> users;

    //Constructor
    private AccountListState() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("AccountListState not implemented yet.");
    }

    //Methods
    public List<UserAccount> getUsers() throws ExecutionControl.NotImplementedException {
        //if (Account.instance().getCurrentUser().instanceof(AdminAccount.instance()))
        //return users;
        throw new ExecutionControl.NotImplementedException("getUsers not implemented yet.");
    }

    public void addUser() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("addUser not implemented yet.");
    }

    public UserAccount removeUser(int userID) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("removeUser not implemented yet.");
    }

    public static Iterator<UserAccount> iterator() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("iterator not implemented yet.");
    }

    /**
     * Performs all necessary tasks before changing state.
     * <p>Make sure to call the context's changeState(this) by the end of run().
     *
     * @author Brandon Watkins
     */
    @Override
    public void run() {
        throw new RuntimeException("run not implemented yet.");
    }

    /**
     * Gets this singleton's instance.
     *
     * @return This singleton's instance (concrete Context).
     *
     * @author Brandon Watkins
     */
    public static State instance() {
        throw new RuntimeException("instance not implemented yet.");
    }

}
