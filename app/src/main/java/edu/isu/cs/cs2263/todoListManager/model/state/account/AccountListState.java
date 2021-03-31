/**
 * @author Brandon Watkins, ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.account;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.context.Context;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.UserAccount;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountListState implements State {
    //Instance Variables
    List<Account> users;
    private List<Account> accounts = new ArrayList();

    //Constructor
    private AccountListState() {}

    //Methods
    public List<Account> getUsers() {
        if (((AccountContext)(AccountContext.instance())).getCurrentAccount() instanceof AdminAccount) return users;
        return new ArrayList<Account>();
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
     * Helper class for singleton implementation
     *
     * @author Brandon Watkins
     */
    private static final class Helper {
        private static final State INSTANCE = new AccountListState();
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
