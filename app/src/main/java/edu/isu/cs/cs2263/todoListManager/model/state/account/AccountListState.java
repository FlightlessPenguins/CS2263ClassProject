/**
 * @author Brandon Watkins, ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.state.account;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.Account;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AccountIterator;
import edu.isu.cs.cs2263.todoListManager.model.objects.account.AdminAccount;
import edu.isu.cs.cs2263.todoListManager.model.state.State;
import edu.isu.cs.cs2263.todoListManager.model.state.SystemState;
import edu.isu.cs.cs2263.todoListManager.model.state.taskList.TaskListInfoState;
import edu.isu.cs.cs2263.todoListManager.storage.Read;
import edu.isu.cs.cs2263.todoListManager.storage.Write;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountListState implements State, Serializable {
    //Instance Variables
    private List<Account> accounts = new ArrayList();
    private List<Account> state = accounts;

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
        setAccounts(accounts);
    }

    //Methods

    public Account getAccount(int accountID) {
        for (Account account : accounts) {
            if (account.getID() == accountID) return account;
        }
        return null;
    }

    public List<Account> getState() {
        return getAccounts();
    }

    public AccountListState setState(List<Account> accounts) {
        this.accounts = accounts;
        this.state = accounts;
        return this;
    }

    // Just because I'm unsure whether the getters/setters needs to be strictly named for gson to read objects in from files.
    /**
     * If current user is an admin, returns a list of all accounts. Otherwise, returns an empty list of accounts.
     *
     * @return (List<Account>) If user is an admin, returns a list of all accounts. Else returns an empty one.
     *
     * @author Brandon Watkins
     */
    public List<Account> getAccounts() {
        if (((AccountContext)(AccountContext.instance())).getCurrentAccount() instanceof AdminAccount) return getAccountsBackdoor();
        return new ArrayList<Account>();
    }

    /**
     * Sets the list of accounts.
     *
     * @param accounts (List<Account>) List of all accounts.
     *
     * @author Brandon Watkins
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        this.state = accounts;
    }

    /**
     * Gets a list of all accounts.
     * <p>Note: This should NOT be called for anything other than saving users to file, or other internal purposes (that don't require a user to be logged in for).
     *
     * @return (List<Account>) A list of all accounts.
     *
     * @author Brandon Watkins
     */
    public List<Account> getAccountsBackdoor() {
        if (accounts == null || accounts.size() < 1) accounts = Read.readAllUserData();
        return accounts;
    }

    public static Iterator<Account> iterator() {
        return new AccountIterator();
    }

    /**
     * Performs all necessary tasks before changing state.
     * <p>Make sure to call the context's changeState(this) by the end of run().
     *
     * @author Brandon Watkins
     * @param state
     * @param args
     */
    @Override
    public void setNextState(State state, Object args) {
        switch(state.getClass().getSimpleName()) {
            case "AccountLoginState": // They logged out
                ((SystemState) SystemState.instance()).setState(AccountLoginState.instance());
                break;
            case "AccountInfoState": // the admin clicked on someone's profile
                ((SystemState) SystemState.instance()).setState(AccountInfoState.instance());
                break;
            case "TaskListInfoState": // they clicked "Home"
                ((SystemState) SystemState.instance()).setState(TaskListInfoState.instance());
                break;
            // this would allow the admin to change any of the profile data, without more time invested
            // case "AccountUpdateState": // the admin wants to update someone's profile
        }
    }

    public void addAccount(Account account) {
        accounts.add(account);
        state = accounts;
        Write.writeAccountData(account);
    }

    public Account removeAccount(Account account) {
        accounts.remove(account);
        state = accounts;
        return account;
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
