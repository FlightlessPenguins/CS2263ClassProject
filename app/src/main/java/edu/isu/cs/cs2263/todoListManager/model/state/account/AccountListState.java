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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountListState implements State, Serializable {
    //Instance Variables
    private List<Account> accounts = new ArrayList();

    //Constructor
    private AccountListState() {}

    /**
     * Create an AccountListState
     *
     * @param accounts (List<Account>) List of all accounts.
     *
     * @author Brandon Watkins
     */
    public AccountListState(List<Account> accounts) {
        this.accounts = accounts;
    }

    //Methods

    // Just because I'm unsure whether the getters/setters needs to be strictly named for gson to read objects in from files.
    /**
     * If current user is an admin, returns a list of all accounts. Otherwise, returns an empty list of accounts.
     *
     * @return (List<Account>) If user is an admin, returns a list of all accounts. Else returns an empty one.
     *
     * @author Brandon Watkins
     */
    public List<Account> getAccounts() { return getUsers(); }

    /**
     * Sets the list of accounts.
     *
     * @param accounts (List<Account>) List of all accounts.
     *
     * @author Brandon Watkins
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * If current user is an admin, returns a list of all accounts. Otherwise, returns an empty list of accounts.
     *
     * @return (List<Account>) If user is an admin, returns a list of all accounts. Else returns an empty one.
     *
     * @author Brandon Watkins
     */
    public List<Account> getUsers() {
        if (((AccountContext)(AccountContext.instance())).getCurrentAccount() instanceof AdminAccount) return accounts;
        return new ArrayList<Account>();
    }

    /**
     * Gets a list of all accounts.
     * <p>Note: This should NOT be called for anything other than saving users to file, or other internal purposes.
     *
     * @return (List<Account>) A list of all accounts.
     *
     * @author Brandon Watkins
     */
    public List<Account> getAccountsBackdoor() {
        return accounts;
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
