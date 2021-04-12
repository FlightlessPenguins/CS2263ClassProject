/**
 * @author Brandon Watkins
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.model.objects.account;

import edu.isu.cs.cs2263.todoListManager.model.context.AccountContext;
import edu.isu.cs.cs2263.todoListManager.model.state.account.AccountListState;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class AccountIterator implements Iterator<Account> {

    private List<Account> accounts;
    private int counter = 0;

    public AccountIterator() {
        this.accounts = ((AccountListState)(AccountListState.instance())).getAccounts();
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return counter - accounts.size() >= 0;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Account next() {
        if (!hasNext()) throw new NoSuchElementException("No more elements");
        return accounts.get(counter++);
    }
}
